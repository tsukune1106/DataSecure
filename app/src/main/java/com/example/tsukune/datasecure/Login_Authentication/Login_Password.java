package com.example.tsukune.datasecure.Login_Authentication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserRepository;
import java.util.List;

public class Login_Password extends Fragment {

    private UserRepository userRepository;
    private TextView TextView_Welcome;
    private List<User> userList = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_password, container, false);



        User user = userList.get(0);

        TextView_Welcome = view.findViewById(R.id.textView_Welcome);

        String welcome = "Welcome Back,\n" + "<b>" + user.getUsername() + "<\b>";
        TextView_Welcome.setText(Html.fromHtml(welcome));

        return view;


    }
}
