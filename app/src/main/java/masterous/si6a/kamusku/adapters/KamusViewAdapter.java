package masterous.si6a.kamusku.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import masterous.si6a.kamusku.databinding.ItemKamusBinding;
import masterous.si6a.kamusku.models.Kamus;
import masterous.si6a.kamusku.utilities.ItemClickListener;

public class KamusViewAdapter extends RecyclerView.Adapter<KamusViewAdapter.ViewHolder> {
    private List<Kamus> data = new ArrayList<>();
    private ItemClickListener<Kamus> itemClickListener;

    public void setOnItemClickListener(ItemClickListener<Kamus> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<Kamus> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KamusViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemKamusBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull KamusViewAdapter.ViewHolder holder, int position) {
        int pos = holder.getAdapterPosition();
        Kamus kamus = data.get(pos);
        holder.itemKamusBinding.tvTitle.setText(kamus.getTitle());
        holder.itemKamusBinding.tvDescription.setText(kamus.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(kamus, pos);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickListener.onItemLongClick(kamus, pos);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemKamusBinding itemKamusBinding;

        public ViewHolder(@NonNull ItemKamusBinding itemView) {
            super(itemView.getRoot());
            itemKamusBinding = itemView;
        }
    }
}