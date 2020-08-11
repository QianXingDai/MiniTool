package com.kakacat.minitool.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.RecycleViewListener;
import com.kakacat.minitool.main.model.MainItem;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainItem> itemList;
    private LayoutInflater inflater;
    private RecycleViewListener.OnItemClick listener;

    public MainAdapter(List<MainItem> itemList) {
        this.itemList = itemList;
    }

    public void setOnClickListener(RecycleViewListener.OnItemClick listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.main_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        MainItem item = itemList.get(position);
        holder.imageView.setActualImageResource(item.getIconId());
        holder.title.setText(item.getTitleId());
        holder.note.setText(item.getNoteId());

        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onClick(holder.itemView, position));
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageView;
        private TextView title;
        private TextView note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            note = itemView.findViewById(R.id.note);
        }
    }

}
