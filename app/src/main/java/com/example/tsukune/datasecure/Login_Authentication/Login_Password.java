package com.example.tsukune.datasecure.Login_Authentication;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tsukune.datasecure.LocalDB.UserDatabase;
import com.example.tsukune.datasecure.R;

public class Login_Password extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_password, container, false);

        UserDatabase.getInstance(this.getContext());

        return view;


    }
}
