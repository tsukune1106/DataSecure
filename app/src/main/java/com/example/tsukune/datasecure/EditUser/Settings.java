package com.example.tsukune.datasecure.EditUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import com.example.tsukune.datasecure.R;

public class Settings extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btn_Edit_User_Login, btn_Edit_User_PS, btn_Edit_User_FS;

    @Override
    public void onBackPressed() {
        btn_Edit_User_Login.setVisibility(View.VISIBLE);
        btn_Edit_User_PS.setVisibility(View.VISIBLE);
        btn_Edit_User_FS.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        btn_Edit_User_Login = findViewById(R.id.btn_Edit_User_Login);
        btn_Edit_User_PS = findViewById(R.id.btn_Edit_User_PS);
        btn_Edit_User_FS = findViewById(R.id.btn_Edit_User_FS);

        btn_Edit_User_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonGone();
                Fragment frg = new Edit_User_Login();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, frg).addToBackStack(null).commit();
            }
        });

        btn_Edit_User_PS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonGone();
                Fragment frg = new Edit_User_PS();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, frg).addToBackStack(null).commit();
            }
        });

        btn_Edit_User_FS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonGone();
                Fragment frg = new Edit_User_FS();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().replace(R.id.fragment_container, frg).addToBackStack(null).commit();
            }
        });
    }

    public void ButtonGone () {
        btn_Edit_User_Login.setVisibility(View.GONE);
        btn_Edit_User_PS.setVisibility(View.GONE);
        btn_Edit_User_FS.setVisibility(View.GONE);
    }
}
