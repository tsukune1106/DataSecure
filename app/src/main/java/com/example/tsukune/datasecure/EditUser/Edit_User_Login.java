package com.example.tsukune.datasecure.EditUser;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.Login_Authentication.Login_Options;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserRepository;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import java.util.List;
import java.util.regex.Pattern;

public class Edit_User_Login extends Fragment {

//    private Toolbar toolbar;
    private TextInputLayout InputLayout_EditUsername, InputLayout_EditPassword, InputLayout_EditConfirmPassword;
    private EditText Input_EditUsername, Input_EditPassword, Input_EditConfirmPassword;
    private TextView TextView_Edit_Login;
    private Button Btn_edit_password;
    private UserViewModel userViewModel;
    private User user;
//    private static int id;
    private UserRepository.UpdateUserLogin updateUserLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_password, container, false);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//        toolbar = view.findViewById(R.id.toolbar);

        InputLayout_EditUsername = view.findViewById(R.id.inputLayout_NewUsername);
        InputLayout_EditPassword = view.findViewById(R.id.inputLayout_NewPassword);
        InputLayout_EditConfirmPassword = view.findViewById(R.id.inputLayout_ConfirmPassword);
        Input_EditUsername = view.findViewById(R.id.input_NewUsername);
        Input_EditPassword = view.findViewById(R.id.input_NewPassword);
        Input_EditConfirmPassword = view.findViewById(R.id.input_ConfirmPassword);
        TextView_Edit_Login = view.findViewById(R.id.textView_register);
        Btn_edit_password = view.findViewById(R.id.btn_register_password);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
//                id = user.getId();
                Input_EditUsername.setText(user.getUsername());
            }
        });



        getActivity().setTitle("Edit User Login");
        TextView_Edit_Login.setTextColor(Color.RED);
        TextView_Edit_Login.setText("\u25cf All field must be enter." +
                "\n\u25cf Password must contains at least 8 to 12 characters, 1 upper case letter, 1 lower case letter," +
                " and 1 number" +
                "\n\u25cf Confirm Password must be the same as Password");

        InputLayout_EditUsername.setHint("Username");
        InputLayout_EditPassword.setHint("Password");

        Btn_edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputLayout_EditUsername.setError(null);
                InputLayout_EditPassword.setError(null);
                InputLayout_EditConfirmPassword.setError(null);

                if (Input_EditUsername.getText().toString().isEmpty()){
                    InputLayout_EditUsername.setError("Invalid Field!");
                }
                if(!isValidPassword(Input_EditPassword.getText().toString()) && Input_EditPassword.getText().toString().isEmpty()) {
                    InputLayout_EditPassword.setError("Invalid Field!");
                }
                else if (!Input_EditConfirmPassword.getText().toString().equals(Input_EditConfirmPassword.getText().toString())) {
                    InputLayout_EditConfirmPassword.setError("Invalid Field!");
                }
                else {
                    updateUserLogin = new UserRepository.UpdateUserLogin(user.getId(), Input_EditUsername.getText().toString(), Input_EditConfirmPassword.getText().toString());
                    userViewModel.updateUserLogin(updateUserLogin);
                    Toast.makeText(getActivity(), "Edit Successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Login_Options.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    public boolean isValidPassword (final String password) {
        Pattern pattern = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}");
        return pattern.matcher(password).matches();
    }


}
