package com.example.tsukune.datasecure.FS_Menu;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.io.File;

public class Access_File_Function {

    public static int REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION = 1;
    public static int REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 2;

    private boolean isExternalStorageWritable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isExternalStorageReadable() {
        if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean checkWritablePermission(Activity activity) {
        int check = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= 23) {
            //if didn't grant permission
            if (check != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_WRITE_EXTERNAL_STORAGE_PERMISSION);
                return false;
            }
            else {
                return true;
            }
        }
        else {
            //permission is auto granted for sdk < 23 upon installation
            return true;
        }
    }

    public boolean checkReadablePermission(Activity activity) {
        int check = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (Build.VERSION.SDK_INT >= 23) {
            //if didn't grant permission
            if (check != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION);
                return false;
            }
            else {
                return true;
            }
        }
        else {
            //permission is auto granted for sdk < 23 upon installation
            return true;
        }
    }

    public void Check_Encrypted_Directory_Exist(Context context) {

        String internalPath = context.getFilesDir().getAbsolutePath() + "/EncryptedFile";
        File dir = new File(internalPath);
        if (!dir.exists()){
            dir.mkdirs();
        }
    }
    public void Check_Decrypted_Directory_Exist() {

        File dir = new File(Environment.getExternalStorageDirectory(), "DataSecureDecryptedFile");
        if (!dir.exists()){
            dir.mkdirs();
        }
    }
}
