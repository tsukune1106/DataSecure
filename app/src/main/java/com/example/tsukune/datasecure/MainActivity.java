package com.example.tsukune.datasecure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.LocalDB.UserDatabase;
import com.example.tsukune.datasecure.Login_Authentication.Login_Options;
import com.example.tsukune.datasecure.Register_User_Logic.Register_User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private List<User> checkUser;

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        UserDatabase.getInstance(this);
        checkUser = UserDatabase.uInstance.userDao().getUser();

        startWork();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                doWork(20);
//                startWork();
//                finish();
//            }
//        }).start();
    }

    private void doWork(int setProgress) {

        for (int progress = 0; progress < 100; progress+=setProgress) {
            try {
                Thread.sleep(1000);
                progressBar.setProgress(progress);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void startWork() {

        if (checkUser.isEmpty()) {
            startActivity(new Intent(this, Register_User.class));
        }

        startActivity(new Intent(this, Login_Options.class));
    }
}
