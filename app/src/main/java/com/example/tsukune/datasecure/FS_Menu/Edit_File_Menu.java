package com.example.tsukune.datasecure.FS_Menu;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.tsukune.datasecure.R;

import java.io.File;

public class Edit_File_Menu extends DialogFragment {

    private Button btn_decrypt_fs, btn_delete_fs, btn_exit_edit_fs;
    private TextView tv_ein_file_name;
    private String ein_file_name;
    private String internalPath;
    private File_Storage_Menu fsm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add__ps__menu, container, false);

        Bundle b = getArguments();
        ein_file_name = b.getString("FileName");

        internalPath = getContext().getFilesDir() + "/EncryptedFile";

        btn_decrypt_fs = view.findViewById(R.id.btn_decrypt_FS);
        btn_delete_fs = view.findViewById(R.id.btn_delete_FS);
        btn_exit_edit_fs = view.findViewById(R.id.btn_exit_editFS);
        tv_ein_file_name = view.findViewById(R.id.tv_ein_file_name);

        tv_ein_file_name.setText(ein_file_name);

        btn_decrypt_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_delete_fs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = new File(internalPath);
                File file = new File(dir, ein_file_name);
                file.delete();
                fsm.rva.notifyDataSetChanged();
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
