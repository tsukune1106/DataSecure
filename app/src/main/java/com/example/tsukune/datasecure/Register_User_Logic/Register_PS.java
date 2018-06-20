package com.example.tsukune.datasecure.Register_User_Logic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tsukune.datasecure.R;

public class Register_PS extends Fragment {

    private TextView tv_register_ps;
    private TextInputLayout til_NewPSPassword, til_ConfirmPSPassword;
    private EditText et_NewPSPassword, et_ConfirmPSPassword;
    private Button btn_register_ps;
    private String newUsername, newPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_ps, container, false);

        Bundle b = getArguments();
        newUsername = b.getString("newUsername");
        newPassword = b.getString("newPassword");
        Log.i("New Username", newUsername);
        Log.i("New Password", newPassword);

        tv_register_ps = view.findViewById(R.id.tv_register_ps);
        til_NewPSPassword = view.findViewById(R.id.inputLayout_NewPSPassword);
        til_ConfirmPSPassword = view.findViewById(R.id.inputLayout_ConfirmPSPassword);
        et_NewPSPassword = view.findViewById(R.id.et_NewPSPassword);
        et_ConfirmPSPassword = view.findViewById(R.id.et_ConfirmPSPassword);
        btn_register_ps = view.findViewById(R.id.btn_register_ps);

        tv_register_ps.setText("\u25cf All field must be enter." +
                "\n\u25cf New PS Password must contains 6 digits" +
                "\n\u25cf Confirm PS Password must be the same as New " + "\nPS Password");

        btn_register_ps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_NewPSPassword.setError(null);
                til_ConfirmPSPassword.setError(null);

                if (et_NewPSPassword.getText().toString().length() != 6 || et_NewPSPassword.getText().toString().isEmpty()) {
                    til_NewPSPassword.setError("Invalid Field!");
                }
                else if (!et_ConfirmPSPassword.getText().toString().equals(et_NewPSPassword.getText().toString())) {
                    til_ConfirmPSPassword.setError("Invalid Field!");
                }
                else {
                    Fragment frg = new Register_FS();
                    Bundle b = new Bundle();
                    b.putString("newUsername", newUsername);
                    b.putString("newPassword", newPassword);
                    b.putString("newPSPassword", et_ConfirmPSPassword.getText().toString());
                    frg.setArguments(b);
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment_container, frg).addToBackStack(null).commit();
                }
            }
        });

        return view;
    }
}
