package com.example.tsukune.datasecure.PS_Menu;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.tsukune.datasecure.R;

public class Add_PS_Menu extends AppCompatActivity {

    private TextInputLayout til_psName, til_psPassword;
    private EditText et_psName, et_psPassword;
    private Button btn_exit, btn_add_PS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ps__menu);

        til_psName = findViewById(R.id.inputLayout_addPS_Name);
        til_psPassword = findViewById(R.id.inputLayout_addPS_Password);
        et_psName = findViewById(R.id.editText_addPS_Name);
    }
}
