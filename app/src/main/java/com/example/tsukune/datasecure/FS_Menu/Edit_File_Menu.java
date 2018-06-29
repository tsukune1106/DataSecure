package com.example.tsukune.datasecure.FS_Menu;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tsukune.datasecure.R;

import java.io.File;
import java.util.List;

public class Edit_File_Menu extends DialogFragment {

    private Button btn_decrypt_fs, btn_delete_fs, btn_exit_edit_fs;
    private TextView tv_ein_file_name;
    private String ein_file_name;
    private String internalPath;
    private File_Storage_Menu fsm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_edit_fs_menu, container, false);

        Bundle b = getArguments();
        ein_file_name = b.getString("FileName");

        Log.i("Received File Name", ein_file_name);

        fsm = new File_Storage_Menu();
        internalPath = getContext().getFilesDir() + "/EncryptedFile";

        btn_decrypt_fs = view.findViewById(R.id.btn_decrypt_FS);
        btn_delete_fs = view.findViewById(R.id.btn_delete_FS);
        btn_exit_edit_fs = view.findViewById(R.id.btn_exit_editFS);
        tv_ein_file_name = view.findViewById(R.id.tv_ein_file_name);

        tv_ein_file_name.setText(ein_file_name);

        fsm.rv.setAdapter(fsm.rva);

        btn_decrypt_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = new File(internalPath);
                File file = new File(dir, ein_file_name);
                try {
                    fsm.fileDecryption(file, ein_file_name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String decryptedPath = Environment.getExternalStorageDirectory() + "/DataSecureDecryptedFile";
                Toast.makeText(getActivity(), ein_file_name + " is decrypted to " + decryptedPath, Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        btn_delete_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = new File(internalPath);
                File file = new File(dir, ein_file_name);
                file.delete();
                fsm.rva.getItems(fsm.getFileNameList());
                fsm.rva.notifyDataSetChanged();
                Toast.makeText(getActivity(), ein_file_name + " is deleted!", Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        btn_exit_edit_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }
}
