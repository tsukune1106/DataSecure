package com.example.tsukune.datasecure;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tsukune.datasecure.FS_Menu.File_Storage_Menu;
import com.example.tsukune.datasecure.PS_Menu.Password_Storage_Menu;
import org.mindrot.jbcrypt.BCrypt;

public class Access_PS_FS extends DialogFragment {

    private int btn_id;
    private TextInputLayout til_access_ps_fs;
    private EditText et_access_ps_fs;
    private Button btn_access_ps_fs, btn_exit_access_ps_fs;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_access_ps_fs, container, false);

        Bundle b = getArguments();
        btn_id = b.getInt("ButtonID");

        til_access_ps_fs = view.findViewById(R.id.inputLayout_access_ps_fs);
        et_access_ps_fs = view.findViewById(R.id.editText_access_ps_fs);
        btn_access_ps_fs = view.findViewById(R.id.btn_access_ps_fs);
        btn_exit_access_ps_fs = view.findViewById(R.id.btn_exit_access_ps_fs);

        if (btn_id == R.id.btn_FS_Menu) {
            til_access_ps_fs.setHint("FS Password");
        }
        else if (btn_id == R.id.btn_PS_Menu) {
            til_access_ps_fs.setHint("PS Password");
        }

        btn_access_ps_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (btn_id == R.id.btn_FS_Menu) {
                    if(et_access_ps_fs.getText().toString().isEmpty() || !BCrypt.checkpw(et_access_ps_fs.getText().toString(), Menu.fs_password)){
                        til_access_ps_fs.setError("Invalid Password");
                    }
                    else {
                        getDialog().dismiss();
                        startActivity(new Intent(getContext(), File_Storage_Menu.class));
                    }
                }
                else if (btn_id == R.id.btn_PS_Menu) {
                    if(et_access_ps_fs.getText().toString().isEmpty() || !BCrypt.checkpw(et_access_ps_fs.getText().toString(), Menu.ps_password)){
                        til_access_ps_fs.setError("Invalid Password");
                    }
                    else {
//                        til_access_ps_fs.setError(null);
//                        et_access_ps_fs.setText(null);
                        getDialog().dismiss();
                        startActivity(new Intent(getContext(), Password_Storage_Menu.class));
                    }
                }
            }
        });

        btn_exit_access_ps_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
