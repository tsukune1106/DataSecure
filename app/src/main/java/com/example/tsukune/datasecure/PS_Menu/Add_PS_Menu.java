package com.example.tsukune.datasecure.PS_Menu;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tsukune.datasecure.Encryption_Algorithm.Password_Encryption_Algorithm;
import com.example.tsukune.datasecure.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Add_PS_Menu extends DialogFragment {

    private Password_Storage_Menu psMenu;
    private Password_Encryption_Algorithm pea;
    private TextInputLayout til_addPS_Name, til_addPS_Password;
    private EditText et_addPS_Name, et_addPS_Password;
    private Button btn_save_PS, btn_exit_addPS;
    private String encrypted_PSName, encrypted_PSPassword;
    public String encryptionKey;
    private List<String> hashedList;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add__ps__menu, container, false);

        psMenu = new Password_Storage_Menu();
        pea = new Password_Encryption_Algorithm();
        encryptionKey = psMenu.encryptionKey;

        til_addPS_Name = view.findViewById(R.id.inputLayout_addPS_Name);
        til_addPS_Password = view.findViewById(R.id.inputLayout_addPS_Password);
        et_addPS_Name = view.findViewById(R.id.editText_addPS_Name);
        et_addPS_Password = view.findViewById(R.id.editText_addPS_Password);
        btn_save_PS = view.findViewById(R.id.btn_save_PS);
        btn_exit_addPS = view.findViewById(R.id.btn_exit_addPS);

        et_addPS_Name.setText(encryptionKey);

        btn_save_PS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("username", encryptionKey);
                til_addPS_Name.setError(null);
                til_addPS_Password.setError(null);

                if (et_addPS_Name.getText().toString().isEmpty()) {
                    til_addPS_Name.setError("This field cannot be left empty!");
                }
                if (et_addPS_Password.getText().toString().isEmpty()) {
                    til_addPS_Password.setError("This field cannot be left empty!");
                }
                else {
                    hashedList = new ArrayList<>();
                    try {
                        hashedList = pea.Ein_PS_Encryption(encryptionKey, et_addPS_Name.getText().toString(), et_addPS_Password.getText().toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    encrypted_PSName = hashedList.get(0);
                    encrypted_PSPassword = hashedList.get(1);
                    Log.i("Encrypted Key", encryptionKey);
                    Log.i("Encrypted Name", encrypted_PSName);
                    Log.i("Encrypted Password", encrypted_PSPassword);

                    psMenu.Add_New_PS(encrypted_PSName, encrypted_PSPassword);

                    Toast.makeText(getActivity(), "New Password Storage Added!", Toast.LENGTH_LONG).show();
                    getDialog().dismiss();
                }
            }
        });

        btn_exit_addPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    private static class PS_Decryption extends AsyncTask<List<String>, Void, List<String>> {

        private String encryptionKey, PS_Name, PS_Password;
        String Encrypted_PS_Name, Encrypted_PS_Password;
        private List<String> strList;
        Password_Encryption_Algorithm pea = new Password_Encryption_Algorithm();

        public PS_Decryption(String encryptionKey, String PS_Name, String PS_Password) {
            this.encryptionKey = encryptionKey;
            this.PS_Name = PS_Name;
            this.PS_Password = PS_Password;
        }

        @Override
        protected List<String> doInBackground(List<String>... lists) {
            strList = new ArrayList<>();
            try {
                Encrypted_PS_Name = pea.Decrypt(encryptionKey, PS_Name);
                Encrypted_PS_Password = pea.Decrypt(encryptionKey, PS_Password);
            } catch (Exception e) {
                e.printStackTrace();
            }
            strList.add(Encrypted_PS_Name);
            strList.add(Encrypted_PS_Password);
            return strList;
        }
    }
}
