package com.kakacat.minitool.wifipasswordview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnLongClickListener;
import com.kakacat.minitool.wifipasswordview.model.Wifi;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Wifi> wifiList;
    private RecycleViewItemOnLongClickListener longClickListener;

    public Adapter(List<Wifi> wifiList) {
        this.wifiList = wifiList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.wifi_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Wifi wifi = wifiList.get(position);
        holder.tvHead.setText(wifi.getWifiImage());
        holder.tvWifiName.setText(wifi.getWifiName());
        holder.tvWifiPwd.setText(wifi.getWifiPwd());
        if (longClickListener != null) {
            holder.itemView.setOnLongClickListener(v -> {
                longClickListener.onLongClick(v, position);
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return wifiList.size();
    }

    public void setLongClickListener(RecycleViewItemOnLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvHead;
        private TextView tvWifiName;
        private TextView tvWifiPwd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHead = itemView.findViewById(R.id.tv_head);
            tvWifiName = itemView.findViewById(R.id.tv_wifi_name);
            tvWifiPwd = itemView.findViewById(R.id.tv_wifi_pwd);
        }
    }

}
