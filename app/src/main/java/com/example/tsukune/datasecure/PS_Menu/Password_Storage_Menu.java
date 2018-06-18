package com.example.tsukune.datasecure.PS_Menu;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tsukune.datasecure.R;

public class Password_Storage_Menu extends AppCompatActivity {

    private FloatingActionButton fab_AddPS;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__storage__menu);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_add__ps__menu);
        fab_AddPS = findViewById(R.id.btn_AddPS);

        fab_AddPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
}
