package com.example.tsukune.datasecure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.LocalDB.UserDatabase;
import com.example.tsukune.datasecure.Login_Authentication.Login_Options;
import com.example.tsukune.datasecure.Register_User_Logic.Register_User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button B_password, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDatabase.getInstance(this);
        List<User> checkUser = UserDatabase.uInstance.userDao().getUser();

        if (checkUser == null) {
            startActivity(new Intent(this, Register_User.class));
        }
        startActivity(new Intent(this, Login_Options.class));

        B_password = findViewById(R.id.passwordSubmit);
        btn_login = findViewById(R.id.btn_Login);
        final EditText passwordText1 = findViewById(R.id.passwordText1);
        final EditText passwordText2 = findViewById(R.id.passwordText2);
        final TextView passwordView = findViewById(R.id.passwordView);

        B_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hashed = BCrypt.hashpw(passwordText2.getText().toString(), BCrypt.gensalt(10));
                if (BCrypt.checkpw(passwordText1.getText().toString(), hashed)){
                    passwordView.setText("Matched");
                }
                else {
                    passwordView.setText("Not Matched");
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register_User.class));
            }
        });

    }
}
