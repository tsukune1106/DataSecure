package com.example.tsukune.datasecure.PS_Menu;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tsukune.datasecure.Encryption_Algorithm.Password_Encryption_Algorithm;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import com.example.tsukune.datasecure.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Edit_PS_Menu extends DialogFragment {

    private TextInputLayout til_editPS_Name, til_editPS_Password;
    private EditText et_editPS_Name, et_editPS_Password;
    private Button btn_update_ps, btn_delete_ps, btn_exit_editPS;
    private int adapter_id, id;
    private String old_PS_Name, old_PS_Password, new_PS_Name, new_PS_Password;
    private Password_Encryption_Algorithm pea;
    private Password_Storage_Menu psMenu;
    private Password_Storage ps;
    private List<String> newHashedList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_ps_menu, container, false);

        Bundle b = getArguments();
        adapter_id = b.getInt("adapterID");
        id = b.getInt("id");
        old_PS_Name = b.getString("name");
        old_PS_Password = b.getString("password");

        pea = new Password_Encryption_Algorithm();
        psMenu = new Password_Storage_Menu();

        til_editPS_Name = view.findViewById(R.id.inputLayout_editPS_Name);
        til_editPS_Password = view.findViewById(R.id.inputLayout_editPS_Password);
        et_editPS_Name = view.findViewById(R.id.editText_editPS_Name);
        et_editPS_Password = view.findViewById(R.id.editText_editPS_Password);
        btn_update_ps = view.findViewById(R.id.btn_update_PS);
        btn_delete_ps = view.findViewById(R.id.btn_delete_PS);
        btn_exit_editPS = view.findViewById(R.id.btn_exit_editPS);
        Log.i("adapter id", adapter_id + "");
        Log.i("id", id + "");
        Log.i("key", psMenu.encryptionKey);

        try {
            et_editPS_Name.setText(pea.Decrypt(psMenu.encryptionKey, old_PS_Name));
            et_editPS_Password.setText(pea.Decrypt(psMenu.encryptionKey, old_PS_Password));
        } catch (Exception e) {
            e.printStackTrace();
        }

        btn_update_ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_editPS_Name.setError(null);
                til_editPS_Password.setError(null);

                if(Validate()) {
                    newHashedList = new ArrayList<>();
                    try {
                        newHashedList = pea.Ein_PS_Encryption(psMenu.encryptionKey, et_editPS_Name.getText().toString(), et_editPS_Password.getText().toString());
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    new_PS_Name = newHashedList.get(0);
                    new_PS_Password = newHashedList.get(1);
                    ps = new Password_Storage(id, new_PS_Name, new_PS_Password);
                    psMenu.Update_PS(ps);
                }
                Toast.makeText(getActivity(), "Selected Password Storage Edited!", Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        btn_delete_ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psMenu.Delete_PS(id);
                Toast.makeText(getActivity(), "Selected Password Storage Deleted!", Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        btn_exit_editPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    private boolean Validate(){
        boolean check = true;
        if (et_editPS_Name.getText().toString().isEmpty()) {
            til_editPS_Name.setError("This field cannot be left empty!");
            check = false;
        }
        if (et_editPS_Password.getText().toString().isEmpty()) {
            til_editPS_Password.setError("This field cannot be left empty!");
            check = false;
        }
        return check;
    }

}
