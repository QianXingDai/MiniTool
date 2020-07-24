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
import com.kakacat.minitool.currencyconversion.model.Country;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Country> countryList;
    private RecycleViewItemOnClickListener listener;

    public CountryAdapter(List<Country> countryList) {
        this.countryList = countryList;
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
        View view = inflater.inflate(R.layout.item_country_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,final int position) {
        Country country = countryList.get(position);
        holder.imageView.setActualImageResource(country.getIconId());
        holder.textView.setText(country.getUnitId());
        if(listener != null){
            holder.itemView.setOnClickListener(v -> listener.onClick(holder.itemView,position));
        }
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.iv_country);
            this.textView = itemView.findViewById(R.id.tv_country_name);
        }
    }
}
