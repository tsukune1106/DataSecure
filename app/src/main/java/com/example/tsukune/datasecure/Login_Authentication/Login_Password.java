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
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.Menu;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserViewModel;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class Login_Password extends Fragment {

    private View view;
    private UserViewModel userViewModel;
    private TextInputLayout inputLayout_login_password;
    private TextView textView_Login_Username;
    private EditText editText_Login_Password;
    private Button btn_Login;
    private User user;
    private String loginPassword, mainPassword;

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
                Log.i("Name", "" + user.getUsername());
                Log.i("Size", "" + users.size());

            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPassword = editText_Login_Password.getText().toString();
                mainPassword = user.getMainPassword();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    if(loginPassword.isEmpty() || !BCrypt.checkpw(loginPassword, mainPassword)) {
                        inputLayout_login_password.setError("Please enter valid password");
                    }
                    else {
                        editText_Login_Password.setText(null);
                        startActivity(new Intent(getActivity(), Menu.class));
                    }
                    }
                });
            }
        });
        return view;
    }
}
