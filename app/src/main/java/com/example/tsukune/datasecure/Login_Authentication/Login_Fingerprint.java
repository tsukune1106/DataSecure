package com.example.tsukune.datasecure.Login_Authentication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tsukune.datasecure.R;

public class Login_Fingerprint extends Fragment {

    private TextView notification;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_fingerprint, container, false);
        return view;
    }

    public void getNotification(String s, boolean b, Context context) {
        notification = view.findViewById(R.id.fingerprint_notification);
        notification.setText(s);

        if (b == false) {
            notification.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        }
        else {
            notification.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }
}
