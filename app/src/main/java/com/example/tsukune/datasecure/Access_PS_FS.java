package com.example.tsukune.datasecure;

import android.app.DialogFragment;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tsukune.datasecure.UserDB.UserViewModel;

public class Access_PS_FS extends DialogFragment {

    private int btn_id;
    private TextInputLayout inputLayout_access_ps_fs;
    private EditText editText_access_ps_fs;
    private Button btn_access_ps_fs, btn_exit_access_ps_fs;
    private String ps_password, fs_password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_access_ps_fs, container, false);

        Bundle b = getArguments();
        btn_id = b.getInt("ButtonID");

        ps_password = Menu.ps_password;
        fs_password = Menu.fs_password;

        inputLayout_access_ps_fs = view.findViewById(R.id.inputLayout_access_ps_fs);
        editText_access_ps_fs = view.findViewById(R.id.editText_access_ps_fs);
        btn_access_ps_fs = view.findViewById(R.id.btn_access_ps_fs);
        btn_exit_access_ps_fs = view.findViewById(R.id.btn_exit_access_ps_fs);

        //switch case

        return view;
    }
}
