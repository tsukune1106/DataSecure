package com.example.tsukune.datasecure;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

public class Register_Password extends Fragment {

    private TextInputLayout InputLayout_NewPassword, InputLayout_ConfirmPassword;
    private EditText Input_NewPassword, Input_ConfirmPassword;
    private Button Btn_register_password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_password, container, false);

        InputLayout_NewPassword = view.findViewById(R.id.inputLayout_NewPassword);
        InputLayout_ConfirmPassword = view.findViewById(R.id.inputLayout_ConfirmPassword);
        Input_NewPassword = view.findViewById(R.id.input_NewPassword);
        Input_ConfirmPassword = view.findViewById(R.id.input_ConfirmPassword);
        Btn_register_password = view.findViewById(R.id.btn_register_password);

        Btn_register_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputLayout_NewPassword.setError(null);
                InputLayout_ConfirmPassword.setError(null);

                if(!isValidPassword(Input_NewPassword.getText().toString())) {
                    InputLayout_NewPassword.setError("New Password must contains at least 8 to 12 characters, 1 upper case letter, 1 lower case letter, " +
                            "and 1 number");
                }
                else if (!Input_ConfirmPassword.getText().toString().equals(Input_NewPassword.getText().toString())) {
                    InputLayout_ConfirmPassword.setError("Confirm Password is not the same as New Password");
                }
                else {
                    Fragment frg = new Register_Fingerprint();
                    Bundle bundle = new Bundle();
                    bundle.putString("newPassword", Input_ConfirmPassword.getText().toString());
                    frg.setArguments(bundle);
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment_container, frg).addToBackStack(null).commit();
                }
            }
        });

        return view;
    }

    public boolean isValidPassword (final String password) {
//        Pattern pattern;
//        Matcher matcher;
//        final String Password_Pattern = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";
//        pattern = Pattern.compile(Password_Pattern);
//        matcher = pattern.matcher(password);
//        return matcher.matches();
        Pattern pattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}");
        return pattern.matcher(password).matches();
    }
}
