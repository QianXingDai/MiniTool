package com.kakacat.minitool.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.RecycleViewItemOnClickListener;
import com.kakacat.minitool.main.model.MainItem;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainItem> list;
    private LayoutInflater inflater;
    private RecycleViewItemOnClickListener listener;

    public MainAdapter(List<MainItem> list) {
        this.list = list;
    }

    public void setOnClickListener(RecycleViewItemOnClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.main_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        MainItem item = list.get(position);
        holder.imageView.setImageResource(item.getIconId());
        holder.title.setText(item.getTitleId());
        holder.note.setText(item.getNoteId());

        if(listener != null){
            holder.itemView.setOnClickListener(v -> listener.onClick(holder.itemView,position));
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView title;
        TextView note;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon);
            title = itemView.findViewById(R.id.title);
            note = itemView.findViewById(R.id.note);
        }
    }

}
