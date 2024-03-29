package com.example.tsukune.datasecure.Login_Authentication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.tsukune.datasecure.Dialog_ProgressBar;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.Menu;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Login_Password extends Fragment {

    private View view;
    private UserViewModel userViewModel;
    private TextInputLayout inputLayout_login_password;
    private TextView textView_Login_Username;
    private EditText editText_Login_Password;
    private Button btn_Login;
    private User user;
    private String loginPassword, mainPassword;
    private int btn_id;
    private boolean valid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_password, container, false);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        inputLayout_login_password = view.findViewById(R.id.inputLayout_login_password);
        textView_Login_Username = view.findViewById(R.id.textView_Login_Username);
        editText_Login_Password = view.findViewById(R.id.editText_login_Password);
        btn_Login = view.findViewById(R.id.btn_Login);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                textView_Login_Username.setText(user.getUsername());
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            inputLayout_login_password.setError(null);
            btn_id = btn_Login.getId();
            loginPassword = editText_Login_Password.getText().toString();
            mainPassword = user.getMainPassword();
                try {
                    valid = new CheckPassword(btn_id, loginPassword, mainPassword).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(!valid) {
                    inputLayout_login_password.setError("Please enter valid password");
                }
                else {
                    editText_Login_Password.setText(null);
                        inputLayout_login_password.setError(null);
                        startActivity(new Intent(getActivity(), Menu.class));
                }
            }
        });
        return view;
    }

    private class CheckPassword extends AsyncTask<Void, Void, Boolean> {

        private int btn_id;
        private String loginPassword, mainPassword;
        private Dialog_ProgressBar dialog_progressBar;
        private boolean valid = true;

        public CheckPassword(int btn_id, String loginPassword, String mainPassword) {
            this.btn_id = btn_id;
            this.loginPassword = loginPassword;
            this.mainPassword = mainPassword;
        }

        @Override
        protected void onPreExecute() {
            dialog_progressBar = new Dialog_ProgressBar();
            Bundle b = new Bundle();
            b.putInt("ButtonID", btn_id);
            dialog_progressBar.setArguments(b);
            dialog_progressBar.show(getActivity().getFragmentManager(), "Dialog_ProgressBar");
            super.onPreExecute();
        }


        @Override
        protected Boolean doInBackground(Void... voids) {
            if(loginPassword.isEmpty() || !BCrypt.checkpw(loginPassword, mainPassword)){
                return valid = false;
            }
            return valid;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            dialog_progressBar.dismiss();
            super.onPostExecute(aBoolean);
        }
    }
}
