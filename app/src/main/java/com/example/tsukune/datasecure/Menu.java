package com.example.tsukune.datasecure;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.example.tsukune.datasecure.EditUser.Settings;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.Login_Authentication.Login_Options;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import java.util.List;

public class Menu extends AppCompatActivity {

    private UserViewModel userViewModel;
    private Button btn_FS_Menu, btn_PS_Menu, btn_Settings;
    private User user;
    private Access_PS_FS dialog_access_ps_fs;
    public static String ps_password, fs_password, ps_encryptionKey, fs_encryptionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        btn_FS_Menu = findViewById(R.id.btn_FS_Menu);
        btn_PS_Menu = findViewById(R.id.btn_PS_Menu);
        btn_Settings = findViewById(R.id.btn_Settings);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                ps_password = user.getPasswordStorage();
                fs_password = user.getFileStorage();
                ps_encryptionKey = user.getPs_encryptionKey();
                fs_encryptionKey = user.getFs_encryptionKey();
            }
        });

        btn_FS_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccessDialog(btn_FS_Menu.getId());
            }
        });

        btn_PS_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAccessDialog(btn_PS_Menu.getId());
            }
        });

        btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Settings.class));
            }
        });
    }

    public void showAccessDialog(int btn_id) {
        dialog_access_ps_fs = new Access_PS_FS();
        Bundle b = new Bundle();
        b.putInt("ButtonID", btn_id);
        dialog_access_ps_fs.setArguments(b);
        dialog_access_ps_fs.show(getFragmentManager(), "Access_PS_FS");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Login_Options.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        super.onBackPressed();
    }
}
