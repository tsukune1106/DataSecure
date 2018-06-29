package com.example.tsukune.datasecure.FS_Menu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.tsukune.datasecure.Encryption_Algorithm.File_Encyrption_Algorithm;
import com.example.tsukune.datasecure.Encryption_Algorithm.Password_Encryption_Algorithm;
import com.example.tsukune.datasecure.Entity.User;
import com.example.tsukune.datasecure.R;
import com.example.tsukune.datasecure.UserDB.UserViewModel;
import com.obsez.android.lib.filechooser.ChooserDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;

public class File_Storage_Menu extends AppCompatActivity {

    private UserViewModel userViewModel;
    private Password_Encryption_Algorithm pea;
    private User user;
    private Button btn_add_file;
    private RecyclerView rv;
    public static FS_List_RV_Adapter rva;
    private Access_File_Function aff;
    private static String encryptionKey;
    private File chosenFilePath;
    private String chosenFileName;
    private File_Encyrption_Algorithm fea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_storage_menu);

        aff = new Access_File_Function();
        aff.Check_Encrypted_Directory_Exist(this);
        aff.Check_Decrypted_Directory_Exist(this);
        aff.checkWritablePermission(this);
        aff.checkReadablePermission(this);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        pea = new Password_Encryption_Algorithm();

        btn_add_file = findViewById(R.id.btn_add_file);

        rv = findViewById(R.id.rv_fs_list);
        rva = new FS_List_RV_Adapter(this, getFileNameList(), this.getFragmentManager());
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(rva);

        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {
                user = users.get(0);
                try {
                    encryptionKey = pea.Decrypt(user.getFileStorage(), user.getFs_encryptionKey());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //recycler view
        //add button to get file and encrypt it using ecryption and decryption method
        //use dialog at viewholder to get the file to decrypt or delete from the directory
        //encryption into app folder called EcnryptedFile
        //decryption into a directory in internal storage (create new directory)

        btn_add_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooserDialog().with(File_Storage_Menu.this).
                        withStartFile(null).
                        withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File filePath) {
                                chosenFilePath = filePath;
                                chosenFileName = chosenFilePath.getName();
                                Log.i("Chosen File Path", chosenFilePath + "");
                                Log.i("Chosen File Name", chosenFileName);
                                try {
                                    fileEncryption(chosenFilePath, chosenFileName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                rva.notifyDataSetChanged();
                            }
                        }).build().show();
                //encrypt
            }
        });
    }

    public void fileEncryption(File srcFile, String fileName) throws Exception {
        String encryptedFolder = this.getFilesDir().getAbsolutePath() + "/EncryptedFile/";
        File encryptedFile = new File(encryptedFolder, fileName);
        Log.i("Chosen Encrypted File", encryptedFile.toString());
        fea = new File_Encyrption_Algorithm();
        fea.file_encrypt_decrypt(Cipher.ENCRYPT_MODE, encryptionKey, srcFile, encryptedFile);
    }

    @Override
    public void onBackPressed() {
        encryptionKey = null;
        super.onBackPressed();
    }

    public List<String> getFileNameList () {
        String internalPath = this.getFilesDir() + "/EncryptedFile";
        File dir = new File(internalPath);
        File[] files = dir.listFiles();
        List<String> fileNameList = new ArrayList<>();

        if (files.length == 0) {
            return null;
        }
        else {
            for (int i = 0; i < files.length; i++) {
                fileNameList.add(files[i].getName());
            }
        }
        if (fileNameList == null ){
            Log.i("file name", "file name list is null");
        }
        else {
            for(String s : fileNameList) {

                Log.i("file name", s);
        }
        }
        return fileNameList;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == Access_File_Function.REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted to add file now!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Permission Denied to add file!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
        else if(requestCode == Access_File_Function.REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION) {
            int grantResultsLength = grantResults.length;
            if (grantResultsLength > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted to read file!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Permission Denied to read file!", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
