package com.example.tsukune.datasecure.Login_Authentication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserViewModel;

import java.util.List;

public class Login_Password extends Fragment {

    private View view;
    private UserViewModel userViewModel;
    private TextView textView_Login_Username;
    private EditText editText_Login_Password;
    private Button btn_Login;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_password, container, false);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        textView_Login_Username = view.findViewById(R.id.textView_Login_Username);
        editText_Login_Password = view.findViewById(R.id.editText_login_Password);
        btn_Login = view.findViewById(R.id.btn_Login);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                textView_Login_Username.setText(user.getUsername());
                Log.i("Name", "" + user.getUsername());
                Log.i("Size", "" + users.size());

            }
        });

        return view;


    }
}
