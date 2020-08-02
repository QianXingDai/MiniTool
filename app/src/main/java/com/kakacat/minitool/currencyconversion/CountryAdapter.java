package com.kakacat.minitool.currencyconversion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnClickListener;
import com.kakacat.minitool.currencyconversion.model.CountryBean;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<CountryBean> countryBeanList;
    private RecycleViewItemOnClickListener listener;

    public CountryAdapter(List<CountryBean> countryBeanList) {
        this.countryBeanList = countryBeanList;
    }


    public void setOnClickListener(RecycleViewItemOnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.item_country_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        CountryBean countryBean = countryBeanList.get(position);
        holder.imageView.setActualImageResource(countryBean.getIconId());
        holder.textView.setText(countryBean.getUnitId());
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onClick(holder.itemView, position));
        }
    }

    @Override
    public int getItemCount() {
        return countryBeanList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_country);
            this.textView = itemView.findViewById(R.id.tv_country_name);
        }
    }
}
