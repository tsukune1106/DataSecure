package com.example.tsukune.datasecure.PS_Menu;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import com.example.tsukune.datasecure.PasswordStorageDB.PasswordStorageViewModel;
import com.example.tsukune.datasecure.R;

public class Password_Storage_Menu extends AppCompatActivity {

    private PasswordStorageViewModel psViewModel;
    private FloatingActionButton fab_AddPS;
    private Dialog dialog_AddPS, dialog_EditPS;

    //for dialog_AddPS
    private TextInputLayout til_addPS_Name, til_addPS_Password;
    private EditText et_addPS_Name, et_addPS_Password;
    private Button btn_save_PS, btn_exit_addPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__storage__menu);

        psViewModel = ViewModelProviders.of(this).get(PasswordStorageViewModel.class);

        //for PS_Menu
        dialog_AddPS = new Dialog(this);
        dialog_AddPS.setContentView(R.layout.activity_add__ps__menu);
        dialog_EditPS = new Dialog(this);
        fab_AddPS = findViewById(R.id.btn_AddPS);

        //for dialog_AddPS
        til_addPS_Name = dialog_AddPS.findViewById(R.id.inputLayout_addPS_Name);
        til_addPS_Password = dialog_AddPS.findViewById(R.id.inputLayout_addPS_Password);
        et_addPS_Name = dialog_AddPS.findViewById(R.id.editText_addPS_Name);
        et_addPS_Password = dialog_AddPS.findViewById(R.id.editText_addPS_Password);
        btn_save_PS = dialog_AddPS.findViewById(R.id.btn_save_PS);
        btn_exit_addPS = dialog_AddPS.findViewById(R.id.btn_exit_addPS);

        //for PS_Menu
        fab_AddPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_addPS_Name.setError(null);
                til_addPS_Password.setError(null);
                et_addPS_Name.setText(null);
                et_addPS_Password.setText(null);
                dialog_AddPS.show();
            }
        });

        //for dialog_AddPS
        btn_save_PS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_addPS_Name.setError(null);
                til_addPS_Password.setError(null);

                if (et_addPS_Name.getText().toString().isEmpty()) {
                    til_addPS_Name.setError("This field cannot be left empty!");
                }
                if (et_addPS_Password.getText().toString().isEmpty()) {
                    til_addPS_Password.setError("This field cannot be left empty!");
                }
                else {
                    Password_Storage ps = new Password_Storage(et_addPS_Name.getText().toString(), et_addPS_Password.getText().toString());
                    psViewModel.addPS(ps);

                    Toast.makeText(dialog_AddPS.getContext(), "New Password Storage Added!",
                            Toast.LENGTH_LONG).show();
                    dialog_AddPS.dismiss();
                }

            }
        });

        btn_exit_addPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_AddPS.dismiss();
            }
        });

        //for dialog_EditPS
    }
}
