package com.example.tsukune.datasecure.PS_Menu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.PasswordStorageDB.PasswordStorageViewModel;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserRepository;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import java.util.List;

public class Password_Storage_Menu extends AppCompatActivity {

    private UserRepository userRepository;
    private UserViewModel userViewModel;
    private PasswordStorageViewModel psViewModel;
    private FloatingActionButton fab_AddPS;
    private User user;

    //for dialog_AddPS
    private Add_PS_Menu dialog_AddPS;
    public static String encryptionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__storage__menu);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        psViewModel = ViewModelProviders.of(this).get(PasswordStorageViewModel.class);

        fab_AddPS = findViewById(R.id.btn_AddPS);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                Log.i("username", user.getUsername());
                encryptionKey = user.getUsername();
            }
        });

        fab_AddPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_AddPS = new Add_PS_Menu();
                dialog_AddPS.show(getFragmentManager(), "Add_PS_Menu");
            }
        });
    }

    public void Add_New_PS (String name, String password){
        Password_Storage ps = new Password_Storage(name, password);
        Log.i("PS name", ps.getPs_Name());
        Log.i("PS pw", ps.getPs_Password());
//        psViewModel.addPS(ps);
    }


}
