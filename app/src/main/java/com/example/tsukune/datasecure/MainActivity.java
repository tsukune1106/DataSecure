package com.example.tsukune.datasecure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.mindrot.jbcrypt.BCrypt;

public class MainActivity extends AppCompatActivity {

    private Button B_password, btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        B_password = findViewById(R.id.passwordSubmit);
        btn_login = (Button) findViewById(R.id.btn_Login);
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
                startActivity(new Intent(MainActivity.this, Register_Options.class));
            }
        });

    }
}
