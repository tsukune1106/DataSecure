package com.example.tsukune.datasecure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tsukune.datasecure.PS_Menu.Add_PS_Menu;
import com.example.tsukune.datasecure.PS_Menu.Password_Storage_Menu;

public class Menu extends AppCompatActivity {

    private Button btn_FS_Menu, btn_PS_Menu, btn_Settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_FS_Menu = findViewById(R.id.btn_FS_Menu);
        btn_PS_Menu = findViewById(R.id.btn_PS_Menu);
        btn_Settings = findViewById(R.id.btn_Settings);

        btn_FS_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, Add_PS_Menu.class));
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
                startActivity(new Intent(Menu.this, Add_PS_Menu.class));
            }
        });
    }
}
