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
import com.example.tsukune.datasecure.PS_Menu.Password_Storage_Menu;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import java.util.List;

public class Menu extends AppCompatActivity {

    private UserViewModel userViewModel;
    private Button btn_FS_Menu, btn_PS_Menu, btn_Settings;
    private static User user;
    public static String ps_Password, fs_Password;

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
//                ps_Password = user.getPasswordStorage();
//                fs_Password = user.getFileStorage();
            }
        });

        btn_FS_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Menu.this, Password_Storage_Menu.class));
            }
        });

        btn_PS_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Password_Storage_Menu.class));
            }
        });

        btn_Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Settings.class));
            }
        });
    }
}
