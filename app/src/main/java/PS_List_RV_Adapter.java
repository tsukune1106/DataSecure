import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tsukune.datasecure.Entity.Password_Storage;
import com.example.tsukune.datasecure.R;

import java.util.List;

public class PS_List_RV_Adapter extends RecyclerView.Adapter<PS_List_RV_Adapter.PSViewHolder> {

    private List<Password_Storage> psList;
    private View.OnLongClickListener onLongClickListener;
    private LayoutInflater layoutInflater;

    public PS_List_RV_Adapter(List<Password_Storage> psList, View.OnLongClickListener onLongClickListener) {
        this.psList = psList;
        this.onLongClickListener = onLongClickListener;
    }

    class PSViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_ein_PS_Name, tv_ein_PS_Password;

        private PSViewHolder(View view) {
            super(view);
            tv_ein_PS_Name = view.findViewById(R.id.tv_ein_PS_Name);
            tv_ein_PS_Password = view.findViewById(R.id.tv_ein_PS_Password);
        }
    }

    @NonNull
    @Override
    public PSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.rv_ps_list, parent, false);
        return new PSViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PSViewHolder holder, int position) {
        Password_Storage ps = psList.get(position);
        holder.tv_ein_PS_Name.setText(ps.getPs_Name());
        holder.tv_ein_PS_Password.setText(ps.getPs_Password());
        holder.itemView.setOnLongClickListener(onLongClickListener);
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
}
