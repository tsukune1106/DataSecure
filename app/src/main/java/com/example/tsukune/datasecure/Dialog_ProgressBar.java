package com.example.tsukune.datasecure;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Dialog_ProgressBar extends DialogFragment {
    private TextView tv;
    private int btn_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_progressbar, container, false);

        Bundle b = getArguments();
        btn_id = b.getInt("ButtonID");

        tv = view.findViewById(R.id.textView);

        if(btn_id == R.id.btn_Login){
            tv.setText("Validating Password, Please Wait...");
        }

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
