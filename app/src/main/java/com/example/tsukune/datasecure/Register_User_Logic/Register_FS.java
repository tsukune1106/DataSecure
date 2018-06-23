package com.example.tsukune.datasecure.Register_User_Logic;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.tsukune.datasecure.MainActivity;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;

public class Register_FS extends Fragment {

    private TextView tv_register_fs;
    private TextInputLayout til_NewFSPassword, til_ConfirmFSPassword;
    private EditText et_NewFSPassword, et_ConfirmFSPassword;
    private Button btn_register_fs;
    private String newUsername, newPassword, newPSPassword;
    private String hashedNewPassword, hashedPSPassword, hashedFSPassword;
    private UserViewModel userViewModel;
    private List<String> pwList, hashedList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_fs, container, false);

        Bundle b = getArguments();
        newUsername = b.getString("newUsername");
        newPassword = b.getString("newPassword");
        newPSPassword = b.getString("newPSPassword");
        Log.i("New Username", newUsername);
        Log.i("New Password", newPassword);
        Log.i("New PS Password", newPSPassword);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        tv_register_fs = view.findViewById(R.id.tv_register_fs);
        til_NewFSPassword = view.findViewById(R.id.inputLayout_NewFSPassword);
        til_ConfirmFSPassword = view.findViewById(R.id.inputLayout_ConfirmFSPassword);
        et_NewFSPassword = view.findViewById(R.id.et_NewFSPassword);
        et_ConfirmFSPassword = view.findViewById(R.id.et_ConfirmFSPassword);
        btn_register_fs = view.findViewById(R.id.btn_register_fs);

        tv_register_fs.setText("\u25cf All field must be enter." +
                "\n\u25cf New PS Password must contains 6 digits" +
                "\n\u25cf Confirm PS Password must be the same as New " + "\nPS Password");

        btn_register_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_NewFSPassword.setError(null);
                til_ConfirmFSPassword.setError(null);

                if (et_NewFSPassword.getText().toString().length() != 6 || et_NewFSPassword.getText().toString().isEmpty()) {
                    til_NewFSPassword.setError("Invalid Field!");
                } else if (!et_ConfirmFSPassword.getText().toString().equals(et_NewFSPassword.getText().toString())) {
                    til_ConfirmFSPassword.setError("Invalid Field!");
                } else {
                    pwList = new ArrayList<>();
                    hashedList = new ArrayList<>();
                    pwList.add(newPassword);
                    pwList.add(newPSPassword);
                    pwList.add(et_ConfirmFSPassword.getText().toString());
                    try {
                        hashedList = new GenerateHashPw(newPassword, newPSPassword, et_ConfirmFSPassword.getText().toString()).execute(pwList).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hashedNewPassword = hashedList.get(0);
                    hashedPSPassword = hashedList.get(1);
                    hashedFSPassword = hashedList.get(2);
                    Log.i("New Password", hashedNewPassword);
                    Log.i("New PS Password", hashedPSPassword);
                    Log.i("New FS Password", hashedFSPassword);
                    User user = new User(newUsername, hashedNewPassword, hashedPSPassword, hashedFSPassword);
                    userViewModel.addUser(user);
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    private class GenerateHashPw extends AsyncTask<List<String>, Void, List<String>> {

        private String newPassword, newPSPassword, newFSPassword;
        private String hashedNewPassword, hashedPSPassword, hashedFSPassword;
        private List<String> strList;

        public GenerateHashPw(String newPassword, String newPSPassword, String newFSPassword) {
            this.newPassword = newPassword;
            this.newPSPassword = newPSPassword;
            this.newFSPassword = newFSPassword;
        }

        @Override
        protected List<String> doInBackground(List<String>... string) {
            strList = new ArrayList<>();
            hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(10));
            hashedPSPassword = BCrypt.hashpw(newPSPassword, BCrypt.gensalt(10));
            hashedFSPassword = BCrypt.hashpw(newFSPassword, BCrypt.gensalt(10));
            strList.add(hashedNewPassword);
            strList.add(hashedPSPassword);
            strList.add(hashedFSPassword);
            return strList;
        }
    }
}
