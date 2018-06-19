package com.example.tsukune.datasecure.PS_Menu;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsukune.datasecure.R;

public class Password_Storage_Menu extends AppCompatActivity {

    private FloatingActionButton fab_AddPS;

    //for dialog_AddPS
    private Button btn_save_PS, btn_exit_addPS;
    private Dialog dialog_AddPS;
    private TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__storage__menu);

        dialog_AddPS = new Dialog(this);
        dialog_AddPS.setContentView(R.layout.activity_add__ps__menu);
        fab_AddPS = findViewById(R.id.btn_AddPS);

        //for dialog_AddPS
        btn_save_PS = dialog_AddPS.findViewById(R.id.btn_save_PS);
        btn_exit_addPS = dialog_AddPS.findViewById(R.id.btn_exit_addPS);
        editText = dialog_AddPS.findViewById(R.id.textView2);

        fab_AddPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_AddPS.show();
            }
        });

        //for dialog_AddPS
        btn_save_PS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(dialog_AddPS.getContext(), "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
                editText.setText("Fk this shit");
            }
        });

        //test
        btn_exit_addPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(dialog_AddPS.getContext(), "This is my Toast message!",
                        Toast.LENGTH_LONG).show();
                dialog_AddPS.dismiss();
            }
        });
    }
}
