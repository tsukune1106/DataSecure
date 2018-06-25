package com.example.tsukune.datasecure.EditUser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsukune.datasecure.Encryption_Algorithm.Password_Encryption_Algorithm;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.Login_Authentication.Login_Options;
import com.example.tsukune.datasecure.MainActivity;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.Register_User_Logic.Register_FS;
import com.example.tsukune.datasecure.UserDB.UserRepository;
import com.example.tsukune.datasecure.UserDB.UserViewModel;

import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class Edit_User_FS extends Fragment {

    private TextView tv_edit_fs;
    private TextInputLayout til_EditFSPassword, til_EditConfirmFSPassword;
    private EditText et_EditFSPassword, et_EditConfirmFSPassword;
    private Button btn_edit_fs;
    private UserViewModel userViewModel;
    private User user;
    private UserRepository.Update_PS_FS update_ps_fs;
    private String old_FS_Password, old_fs_encryptionKey, new_fs_encryptionKey, new_FS_Password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_fs, container, false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        tv_edit_fs = view.findViewById(R.id.tv_register_fs);
        til_EditFSPassword = view.findViewById(R.id.inputLayout_NewFSPassword);
        til_EditConfirmFSPassword = view.findViewById(R.id.inputLayout_ConfirmFSPassword);
        et_EditFSPassword = view.findViewById(R.id.et_NewFSPassword);
        et_EditConfirmFSPassword = view.findViewById(R.id.et_ConfirmFSPassword);
        btn_edit_fs = view.findViewById(R.id.btn_register_fs);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                old_FS_Password = user.getPasswordStorage();
                old_fs_encryptionKey = user.getPs_encryptionKey();
            }
        });

        tv_edit_fs.setText("\u25cf All field must be enter." +
                "\n\u25cf FS Password must contains 6 digits" +
                "\n\u25cf Confirm FS Password must be the same as FS Password");

        til_EditFSPassword.setHint("PS Password");
        til_EditConfirmFSPassword.setHint("Confirm PS Password");

        btn_edit_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_EditFSPassword.setError(null);
                til_EditConfirmFSPassword.setError(null);

                if (et_EditFSPassword.getText().toString().length() != 6 || et_EditFSPassword.getText().toString().isEmpty()) {
                    til_EditFSPassword.setError("Invalid Field!");
                }
                else if (!et_EditConfirmFSPassword.getText().toString().equals(et_EditFSPassword.getText().toString())) {
                    til_EditConfirmFSPassword.setError("Invalid Field!");
                }
                else {
                    new_FS_Password = BCrypt.hashpw(et_EditConfirmFSPassword.getText().toString(), BCrypt.gensalt(10));
                    try {
                        new_fs_encryptionKey = Edit_FS_EncryptionKey(old_FS_Password, new_FS_Password, old_fs_encryptionKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    update_ps_fs = new UserRepository.Update_PS_FS(user.getId(), new_FS_Password, new_fs_encryptionKey);
                    userViewModel.updateFS(update_ps_fs);
                    Toast.makeText(getActivity(), "Edit Successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Login_Options.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    public String Edit_FS_EncryptionKey(String old_fs_password, String new_fs_password, String old_fs_encryptionKey) throws Exception {
        Password_Encryption_Algorithm pea = new Password_Encryption_Algorithm();
        String originalKey = pea.Decrypt(old_fs_password, old_fs_encryptionKey);
        String new_fs_encryptionKey = pea.Encrypt(new_fs_password, originalKey);
        return new_fs_encryptionKey;
    }
}
