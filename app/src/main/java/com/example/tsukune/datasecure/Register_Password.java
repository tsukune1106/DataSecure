package com.example.tsukune.datasecure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import org.mindrot.jbcrypt.BCrypt;

public class Register_Password extends Fragment {

    private TextInputLayout InputLayout_NewPassword, InputLayout_ConfirmPassword;
    private String Input_NewPassword;
    private String Input_ConfirmPassword;
    private Button Btn_register_password;
    private String hashed_NewPassword, hashed_ConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_password, container, false);

        InputLayout_NewPassword = view.findViewById(R.id.inputLayout_NewPassword);
        InputLayout_ConfirmPassword = view.findViewById(R.id.inputLayout_ConfirmPassword);
        Btn_register_password = view.findViewById(R.id.btn_register_password);

        Btn_register_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Input_NewPassword = InputLayout_NewPassword.getEditText().getText().toString();
                Input_ConfirmPassword = InputLayout_ConfirmPassword.getEditText().getText().toString();
                hashed_NewPassword = BCrypt.hashpw(Input_NewPassword, BCrypt.gensalt(10));
                hashed_ConfirmPassword = BCrypt.hashpw(Input_ConfirmPassword, BCrypt.gensalt(10));

                //Check whether both new password and confirm password entered are the same
                if (BCrypt.checkpw(hashed_ConfirmPassword, hashed_NewPassword)){
                    //save in database or direct to
                }
                else {
                    InputLayout_ConfirmPassword.setError("Confirm Password is not the same as New Password");
                }
            }
        });

        return view;
    }
}
