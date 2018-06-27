package com.example.tsukune.datasecure.PS_Menu;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tsukune.datasecure.Encryption_Algorithm.Password_Encryption_Algorithm;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import com.example.tsukune.datasecure.R;
import java.util.ArrayList;
import java.util.List;

public class PS_List_RV_Adapter extends RecyclerView.Adapter<PS_List_RV_Adapter.PSViewHolder> {

    private Context context;
    private List<Password_Storage> psList;
    private List<String> decryptedList;
    private Password_Encryption_Algorithm pea;
    private FragmentManager fm;

    public PS_List_RV_Adapter(Context c, List<Password_Storage> psList, FragmentManager fm) {
        this.context = c;
        this.psList = psList;
        this.fm = fm;
    }

    class PSViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_ein_PS_Name, tv_ein_PS_Password;
//        private OnItemClickListener onItemClickListener;

        private PSViewHolder(View view) {
            super(view);
            tv_ein_PS_Name = view.findViewById(R.id.tv_ein_PS_Name);
            tv_ein_PS_Password = view.findViewById(R.id.tv_ein_PS_Password);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Password_Storage ps = psList.get(getAdapterPosition());

            Edit_PS_Menu dialog_editPsMenu = new Edit_PS_Menu();
            Bundle b = new Bundle();
            b.putInt("adapterID", getAdapterPosition());
            b.putInt("id", ps.getId());
            b.putString("name", ps.getPs_Name());
            b.putString("password", ps.getPs_Password());
            dialog_editPsMenu.setArguments(b);
            dialog_editPsMenu.show(fm, "Edit_PS_Menu");
        }
    }

    @NonNull
    @Override
    public PSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_ps_list, parent, false);
        return new PSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PSViewHolder holder, int position) {
        Password_Storage ps = psList.get(position);
        pea = new Password_Encryption_Algorithm();
        decryptedList = new ArrayList<>();
        try {
            decryptedList = pea.Ein_PS_Decryption(Password_Storage_Menu.encryptionKey, ps.getPs_Name(), ps.getPs_Password());
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.tv_ein_PS_Name.setText(decryptedList.get(0));
        holder.tv_ein_PS_Password.setText(decryptedList.get(1));
    }

    @Override
    public int getItemCount() {
        if (psList != null) {
            return psList.size();
        }
        else {
            return 0;
        }
    }

    public void addItems(List<Password_Storage> psList) {
        this.psList = psList;
        notifyDataSetChanged();
    }
}
