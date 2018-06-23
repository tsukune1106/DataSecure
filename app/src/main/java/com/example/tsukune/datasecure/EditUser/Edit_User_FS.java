package com.example.tsukune.datasecure.EditUser;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserViewModel;

import java.util.List;

public class Edit_User_FS extends Fragment {

    private TextView tv_register_fs;
    private TextInputLayout til_NewFSPassword, til_ConfirmFSPassword;
    private EditText et_NewFSPassword, et_ConfirmFSPassword;
    private Button btn_register_fs;
    private String newUsername, newPassword, newPSPassword;
    private String hashedNewPassword, hashedPSPassword, hashedFSPassword;
    private UserViewModel userViewModel;
    private List<String> pwList, hashedList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_fs, container, false);

        return view;
    }
}
