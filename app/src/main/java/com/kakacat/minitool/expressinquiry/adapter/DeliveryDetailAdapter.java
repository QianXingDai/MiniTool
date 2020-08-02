package com.kakacat.minitool.expressinquiry.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.expressinquiry.model.PackageDetail;

import java.util.List;

public class DeliveryDetailAdapter extends RecyclerView.Adapter<DeliveryDetailAdapter.ViewHolder> {
    private static final int HEAD_VIEW = 1;

    private List<PackageDetail> packageDetailList;
    private Context context;
    private LayoutInflater inflater;

    public DeliveryDetailAdapter(List<PackageDetail> packageDetailList) {
        this.packageDetailList = packageDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(context);
        }
        View view;
        if (viewType == HEAD_VIEW) {
            view = inflater.inflate(R.layout.delivery_detail_head, parent, false);
        } else {
            view = inflater.inflate(R.layout.delivery_detail_item, parent, false);
        }
        return new ViewHolder(view, viewType);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (holder.isHeadView) {
            holder.tvCostTime.setText("5678");
        } else {
            PackageDetail packageDetail = packageDetailList.get(position);
            String[] times = packageDetail.getTime().split(" ");
            holder.tvUpdateTime1.setText(times[1]);
            holder.tvUpdateTime2.setText(times[0]);
            holder.tvDetail.setText(packageDetail.getContent());
            if (position == 1) {
                int textColor = context.getColor(R.color.colorAccent);
                holder.tvUpdateTime1.setTextColor(textColor);
                holder.tvUpdateTime2.setTextColor(textColor);
                holder.tvDetail.setTextColor(textColor);
            }
        }

    }

    @Override
    public int getItemCount() {
        return packageDetailList == null ? 0 : packageDetailList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_VIEW;
        }
        return super.getItemViewType(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUpdateTime1;
        private TextView tvUpdateTime2;
        private TextView tvDetail;

        private boolean isHeadView;
        private TextView tvCostTime;

        public ViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            if (viewType == HEAD_VIEW) {
                isHeadView = true;
                tvCostTime = itemView.findViewById(R.id.tv_cost_time);
            } else {
                isHeadView = false;
                tvUpdateTime1 = itemView.findViewById(R.id.tv_update_time1);
                tvUpdateTime2 = itemView.findViewById(R.id.tv_update_time2);
                tvDetail = itemView.findViewById(R.id.tv_content);
            }
        }
    }
}
