package com.example.tsukune.datasecure.PS_Menu;

import android.app.Dialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsukune.datasecure.R;

public class Add_PS_Menu extends AppCompatActivity {

    private TextInputLayout til_psName, til_psPassword;
    private EditText et_psName, et_psPassword;
    private Button btn_exit_addPS, btn_save_PS;
    private TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__ps__menu);

        til_psName = findViewById(R.id.inputLayout_addPS_Name);
        til_psPassword = findViewById(R.id.inputLayout_addPS_Password);
        et_psName = findViewById(R.id.editText_addPS_Name);
        et_psPassword = findViewById(R.id.editText_addPS_Password);
        btn_save_PS = findViewById(R.id.btn_save_PS);
        btn_exit_addPS = findViewById(R.id.btn_exit_addPS);

        btn_save_PS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Add_PS_Menu.this, "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
                editText.setText("");
                Log.i("Test", et_psName.getText().toString());

            }
        });

        btn_exit_addPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Add_PS_Menu.this, "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
