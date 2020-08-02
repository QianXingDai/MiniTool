package com.kakacat.minitool.expressinquiry.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnClickListener;
import com.kakacat.minitool.expressinquiry.model.Delivery;

import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {

    private List<Delivery> deliveryList;
    private LayoutInflater inflater;
    private RecycleViewItemOnClickListener listener;

    public DeliveryAdapter(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
    }


    public void setItemListener(RecycleViewItemOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.delivery_item, parent, false);
        return new ViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery delivery = deliveryList.get(position);
        holder.tvHead.setText(String.valueOf(delivery.getCompanyName().charAt(0)));
        holder.tvTitle.setText(delivery.getCompanyName() + "(" + delivery.getStatus() + ")");
        holder.tvCompanyTel.setText("电话 : " + delivery.getTel());
        holder.tvUpdateTime.setText(delivery.getUpdateTime());
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onClick(holder.itemView, position));
        }
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvHead;
        private TextView tvTitle;
        private TextView tvCompanyTel;
        private TextView tvUpdateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHead = itemView.findViewById(R.id.head_tv);
            tvTitle = itemView.findViewById(R.id.title_tv);
            tvCompanyTel = itemView.findViewById(R.id.tv_company_tel);
            tvUpdateTime = itemView.findViewById(R.id.update_time_tv);
        }
    }
}
