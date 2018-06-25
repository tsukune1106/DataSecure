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

import com.example.tsukune.datasecure.Encryption_Algorithm.Password_Encryption_Algorithm;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.MainActivity;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import org.mindrot.jbcrypt.BCrypt;

import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Register_FS extends Fragment {

    private TextView tv_register_fs;
    private TextInputLayout til_NewFSPassword, til_ConfirmFSPassword;
    private EditText et_NewFSPassword, et_ConfirmFSPassword;
    private Button btn_register_fs;
    private String newUsername, newPassword, newPSPassword;
    private String hashedNewPassword, hashedPSPassword, hashedFSPassword, ps_encryptionKey, fs_encryptionKey;
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
                "\n\u25cf Confirm FS Password must be the same as New " + "\nFS Password");

        btn_register_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_NewFSPassword.setError(null);
                til_ConfirmFSPassword.setError(null);

                if (et_NewFSPassword.getText().toString().length() != 6 || et_NewFSPassword.getText().toString().isEmpty()) {
                    til_NewFSPassword.setError("Invalid Field!");
                }
                else if (!et_ConfirmFSPassword.getText().toString().equals(et_NewFSPassword.getText().toString())) {
                    til_ConfirmFSPassword.setError("Invalid Field!");
                }
                else {
//                    pwList = new ArrayList<>();
                    hashedList = new ArrayList<>();
//                    pwList.add(newPassword);
//                    pwList.add(newPSPassword);
//                    pwList.add(et_ConfirmFSPassword.getText().toString());
                    try {
                        hashedList = new GenerateHashPw(newPassword, newPSPassword, et_ConfirmFSPassword.getText().toString()).execute().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    hashedNewPassword = hashedList.get(0);
                    hashedPSPassword = hashedList.get(1);
                    hashedFSPassword = hashedList.get(2);
                    ps_encryptionKey = hashedList.get(3);
                    fs_encryptionKey = hashedList.get(4);
                    Log.i("New Password", hashedNewPassword);
                    Log.i("New PS Password", hashedPSPassword);
                    Log.i("New FS Password", hashedFSPassword);
                    Log.i("New PS Key", ps_encryptionKey);
                    Log.i("New FS Key", fs_encryptionKey);
                    User user = new User(newUsername, hashedNewPassword, hashedPSPassword, hashedFSPassword, ps_encryptionKey, fs_encryptionKey);
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
        public String ps_encryptionKey, fs_encryptionKey;
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
            try {
                ps_encryptionKey = GenerateKey(hashedPSPassword);
                fs_encryptionKey = GenerateKey(hashedFSPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
            strList.add(hashedNewPassword);
            strList.add(hashedPSPassword);
            strList.add(hashedFSPassword);
            strList.add(ps_encryptionKey);
            strList.add(fs_encryptionKey);
            return strList;
        }
    }

    public String GenerateKey(String password) throws Exception {
        byte[] salt = password.getBytes();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
        Log.i("key", secret.toString());
        Password_Encryption_Algorithm pea = new Password_Encryption_Algorithm();
        String encryptedKey = pea.Encrypt(password, secret.toString());
        return encryptedKey;
    }
}
