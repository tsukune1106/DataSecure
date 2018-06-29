package com.example.tsukune.datasecure.FS_Menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tsukune.datasecure.R;

import java.util.ArrayList;
import java.util.List;

public class FS_List_RV_Adapter extends RecyclerView.Adapter<FS_List_RV_Adapter.FSViewHolder> {

    private Context context;
    private List<String> fileNameList;
    private FragmentManager fm;

    public FS_List_RV_Adapter(Context context, List<String> fileNameList, FragmentManager fm) {
        this.context = context;
        this.fileNameList = fileNameList;
        this.fm = fm;
    }

    class FSViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_ein_FS_Name;

        private FSViewHolder(View view) {
            super(view);
            tv_ein_FS_Name = view.findViewById(R.id.tv_ein_FS_Name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String ein_fileName = fileNameList.get(getAdapterPosition());

            Edit_File_Menu efm = new Edit_File_Menu();
            Bundle b = new Bundle();
            b.putString("FileName", ein_fileName);
            efm.setArguments(b);
            efm.show(fm, "Edit_File_Menu");
        }
    }

    @NonNull
    @Override
    public FSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_fs_list, parent, false);
        return new FSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FSViewHolder holder, int position) {
        String fileName = fileNameList.get(position);
        holder.tv_ein_FS_Name.setText(fileName);
    }

    @Override
    public int getItemCount() {
        if (fileNameList != null) {
            return fileNameList.size();
        }
        else {
            return 0;
        }
    }

    public void getItems (List<String> fileNameList) {
        this.fileNameList = fileNameList;
        notifyDataSetChanged();
    }
}
