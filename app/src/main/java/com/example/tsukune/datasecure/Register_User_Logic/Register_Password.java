package com.example.tsukune.datasecure.Register_User_Logic;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.tsukune.datasecure.R;
import java.util.regex.Pattern;

public class Register_Password extends Fragment {

    private TextInputLayout InputLayout_NewUsername, InputLayout_NewPassword, InputLayout_ConfirmPassword;
    private EditText Input_NewUsername, Input_NewPassword, Input_ConfirmPassword;
    private TextView TextView_Register;
    private Button Btn_register_password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_password, container, false);

        InputLayout_NewUsername = view.findViewById(R.id.inputLayout_NewUsername);
        InputLayout_NewPassword = view.findViewById(R.id.inputLayout_NewPassword);
        InputLayout_ConfirmPassword = view.findViewById(R.id.inputLayout_ConfirmPassword);
        Input_NewUsername = view.findViewById(R.id.input_NewUsername);
        Input_NewPassword = view.findViewById(R.id.input_NewPassword);
        Input_ConfirmPassword = view.findViewById(R.id.input_ConfirmPassword);
        TextView_Register = view.findViewById(R.id.textView_register);
        Btn_register_password = view.findViewById(R.id.btn_register_password);

        TextView_Register.setTextColor(Color.RED);
        TextView_Register.setText("\u25cf All field must be enter." +
                "\n\u25cf New Password must contains at least 8 to 12 characters, 1 upper case letter, 1 lower case letter," +
                " and 1 number" +
                "\n\u25cf Confirm Password must be the same as New " + "\nPassword");

        Btn_register_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputLayout_NewUsername.setError(null);
                InputLayout_NewPassword.setError(null);
                InputLayout_ConfirmPassword.setError(null);

                if (Validate()) {
                    Fragment frg = new Register_PS();
                    Bundle b = new Bundle();
                    b.putString("newUsername", Input_NewUsername.getText().toString());
                    b.putString("newPassword", Input_ConfirmPassword.getText().toString());
                    frg.setArguments(b);
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment_container, frg).addToBackStack(null).commit();
                }
            }
        });
        return view;
    }

    public boolean isValidPassword (final String password) {
        Pattern pattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}");
        return pattern.matcher(password).matches();
    }

    private boolean Validate(){
        boolean check = true;
        if (Input_NewUsername.getText().toString().isEmpty()) {
            InputLayout_NewUsername.setError("Invalid Field!");
            check = false;
        }
        if (!isValidPassword(Input_NewPassword.getText().toString()) || Input_NewPassword.getText().toString().isEmpty()) {
            InputLayout_NewPassword.setError("Invalid Field!");
            check = false;
        }
        if (!Input_ConfirmPassword.getText().toString().equals(Input_NewPassword.getText().toString())) {
            InputLayout_ConfirmPassword.setError("Invalid Field!");
            check = false;
        }
        return check;
    }
}
