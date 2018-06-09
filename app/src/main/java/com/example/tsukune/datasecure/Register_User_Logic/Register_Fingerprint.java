package com.example.tsukune.datasecure.Register_User_Logic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tsukune.datasecure.R;

public class Register_Fingerprint extends Fragment {

    private String new_Password;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_fingerprint, container, false);

        Bundle b = getArguments();
        new_Password = b.getString("newPassword");
        TextView sample = view.findViewById(R.id.textView);
        sample.setText(new_Password);

        return view;
    }
}
