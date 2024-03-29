package com.example.tsukune.datasecure;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import com.example.tsukune.datasecure.Login_Authentication.Login_Options;
import com.example.tsukune.datasecure.Register_User_Logic.Register_User;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        new Thread(new Runnable() {
            @Override
            public void run() {
                doWork(1);
                try {
                    startWork();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finish();
            }
        }).start();
    }

    private void doWork(int setProgress) {

        for (int progress = 0; progress < 100; progress+=setProgress) {
            try {
                Thread.sleep(50);
                progressBar.setProgress(progress);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void startWork() throws ExecutionException, InterruptedException {

        Log.i("Count", String.valueOf(userViewModel.getCount()));
        if (userViewModel.getCount() > 0) {
            //database not empty
            startActivity(new Intent(this, Login_Options.class));
        }
        else {
            //database is empty
            startActivity(new Intent(this, Register_User.class));
        }


    }
}
