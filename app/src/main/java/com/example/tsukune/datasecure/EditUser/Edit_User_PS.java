package com.example.tsukune.datasecure.EditUser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.tsukune.datasecure.Encryption_Algorithm.Password_Encryption_Algorithm;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.Login_Authentication.Login_Options;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserRepository;
import com.example.tsukune.datasecure.UserDB.UserViewModel;

import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class Edit_User_PS extends Fragment {

    private TextView tv_edit_ps;
    private TextInputLayout til_EditPSPassword, til_EditConfirmPSPassword;
    private EditText et_EditPSPassword, et_EditConfirmPSPassword;
    private Button btn_edit_ps;
    private UserViewModel userViewModel;
    private User user;
    private UserRepository.Update_PS_FS update_ps_fs;
    private String old_PS_Password, old_ps_encryptionKey, new_ps_encryptionKey, new_PS_Password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_ps, container, false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        tv_edit_ps = view.findViewById(R.id.tv_register_ps);
        til_EditPSPassword = view.findViewById(R.id.inputLayout_NewPSPassword);
        til_EditConfirmPSPassword = view.findViewById(R.id.inputLayout_ConfirmPSPassword);
        et_EditPSPassword = view.findViewById(R.id.et_NewPSPassword);
        et_EditConfirmPSPassword = view.findViewById(R.id.et_ConfirmPSPassword);
        btn_edit_ps = view.findViewById(R.id.btn_register_ps);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                old_PS_Password = user.getPasswordStorage();
                old_ps_encryptionKey = user.getPs_encryptionKey();
            }
        });

        tv_edit_ps.setText("\u25cf All field must be enter." +
                "\n\u25cf PS Password must contains 6 digits" +
                "\n\u25cf Confirm PS Password must be the same as PS Password");

        til_EditPSPassword.setHint("PS Password");
        til_EditConfirmPSPassword.setHint("Confirm PS Password");

        btn_edit_ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_EditPSPassword.setError(null);
                til_EditConfirmPSPassword.setError(null);

                if (et_EditPSPassword.getText().toString().length() != 6 || et_EditPSPassword.getText().toString().isEmpty()) {
                    til_EditPSPassword.setError("Invalid Field!");
                }
                else if (!et_EditConfirmPSPassword.getText().toString().equals(et_EditPSPassword.getText().toString())) {
                    til_EditConfirmPSPassword.setError("Invalid Field!");
                }
                else {
                    new_PS_Password = BCrypt.hashpw(et_EditConfirmPSPassword.getText().toString(), BCrypt.gensalt(10));
                    try {
                        new_ps_encryptionKey = Edit_PS_EncryptionKey(old_PS_Password, new_PS_Password, old_ps_encryptionKey);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i("new password", new_PS_Password);
                    Log.i("new encryptionKey", new_ps_encryptionKey);
                    update_ps_fs = new UserRepository.Update_PS_FS(user.getId(), new_PS_Password, new_ps_encryptionKey);
                    userViewModel.updatePS(update_ps_fs);
                    Toast.makeText(getActivity(), "Edit Successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Login_Options.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        return view;
    }

    public String Edit_PS_EncryptionKey(String old_ps_password, String new_ps_password, String old_ps_encryptionKey) throws Exception {
        Password_Encryption_Algorithm pea = new Password_Encryption_Algorithm();
        Log.i("old password", old_ps_password);
        Log.i("old encryptionKey", old_ps_encryptionKey);
        String originalKey = pea.Decrypt(old_ps_password, old_ps_encryptionKey);
        Log.i("original", originalKey);
        String new_ps_encryptionKey = pea.Encrypt(new_ps_password, originalKey);
        Log.i("new password", new_ps_password);
        Log.i("new encryptionKey", new_ps_encryptionKey);
        return new_ps_encryptionKey;
    }
}
