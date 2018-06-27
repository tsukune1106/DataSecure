package com.example.tsukune.datasecure.PS_Menu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.tsukune.datasecure.Encryption_Algorithm.Password_Encryption_Algorithm;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.PasswordStorageDB.PasswordStorageViewModel;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserRepository;
import com.example.tsukune.datasecure.UserDB.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Password_Storage_Menu extends AppCompatActivity {

    private UserViewModel userViewModel;
    private static PasswordStorageViewModel psViewModel;
    private FloatingActionButton fab_AddPS;
    private User user;
    private RecyclerView rv;
    private static PS_List_RV_Adapter rva;
    private Password_Encryption_Algorithm pea;

    //for dialog_AddPS
    private Add_PS_Menu dialog_AddPS;
    public static String encryptionKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password__storage__menu);

        pea = new Password_Encryption_Algorithm();

        rv = findViewById(R.id.rv_ps_list);
        rva = new PS_List_RV_Adapter(this, new ArrayList<Password_Storage>(), this.getFragmentManager());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(rva);

        fab_AddPS = findViewById(R.id.btn_AddPS);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        psViewModel = ViewModelProviders.of(this).get(PasswordStorageViewModel.class);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                try {
                    encryptionKey = pea.Decrypt(user.getPasswordStorage(), user.getPs_encryptionKey());
                    Log.i("ps password", encryptionKey);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        psViewModel.getAllPS().observe(this, new Observer<List<Password_Storage>>() {
            @Override
            public void onChanged(@Nullable List<Password_Storage> psList) {
                if (psList ==  null){
                    //edittext
                }
                else {
                    rva.addItems(psList);
                }
            }
        });

        fab_AddPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_AddPS = new Add_PS_Menu();
                dialog_AddPS.show(getFragmentManager(), "Add_PS_Menu");
            }
        });
    }

    public void Add_New_PS (String name, String password){
        Password_Storage ps = new Password_Storage(name, password);
        Log.i("PS name", ps.getPs_Name());
        Log.i("PS pw", ps.getPs_Password());
        psViewModel.addPS(ps);
    }

    public void Update_PS (Password_Storage ps) {
        psViewModel.updatePS(ps);
    }

    public void Delete_PS (int id){
        psViewModel.deletePSbyID(id);
    }

    @Override
    public void onBackPressed() {
        encryptionKey = null;
        super.onBackPressed();
    }
}
