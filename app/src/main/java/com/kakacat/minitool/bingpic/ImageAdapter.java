package com.kakacat.minitool.bingpic;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnClickListener;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnLongClickListener;
import com.kakacat.minitool.common.myinterface.RecycleViewOnTouchListener;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private List<String> addressList;
    private LayoutInflater inflater;

    private RecycleViewItemOnClickListener onClickListener;
    private RecycleViewItemOnLongClickListener onLongClickListener;
    private RecycleViewOnTouchListener onTouchListener;

    public ImageAdapter(List<String> addressList) {
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.bing_pic_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sdv.setImageURI(addressList.get(position));

        if (onClickListener != null)
            holder.sdv.setOnClickListener(v -> onClickListener.onClick(holder.itemView, position));
        if (onLongClickListener != null) {
            holder.sdv.setOnLongClickListener(v -> {
                onLongClickListener.onLongClick(holder.sdv, position);
                return false;
            });
        }
        if (onTouchListener != null) {
            holder.sdv.setOnTouchListener((v, event) -> {
                onTouchListener.onTouch(v, event);
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public void setOnClickListener(RecycleViewItemOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(RecycleViewItemOnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public void setOnTouchListener(RecycleViewOnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView sdv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.image_view);
        }
    }
}
