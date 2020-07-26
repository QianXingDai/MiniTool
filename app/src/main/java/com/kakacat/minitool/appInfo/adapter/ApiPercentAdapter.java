package com.kakacat.minitool.appInfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.appInfo.model.ApiPercent;

import java.util.List;

public class ApiPercentAdapter extends RecyclerView.Adapter<ApiPercentAdapter.ViewHolder> {
    private static final int HEADER_VIEW_TYPE = 1;

    private List<ApiPercent> modelList;
    private LayoutInflater inflater;

    public ApiPercentAdapter(List<ApiPercent> modelList) {
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        if(viewType == HEADER_VIEW_TYPE){
            view = inflater.inflate(R.layout.header_api_percent,parent,false);
        }else{
            view = inflater.inflate(R.layout.item_api_percent,parent,false);
        }

        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position != 0){
            ApiPercent model = modelList.get(position - 1);
            holder.sdvAndroidIcon.setActualImageResource(model.getIconId());
            holder.tvAndroidVersionName.setText(model.getVersionName());
            holder.tvApiLevel.setText(model.getVersionApi());
            holder.tvAppNum.setText(String.valueOf(model.getAppNum()));
            holder.tvAppPercent.setText(model.getAppPercent());
        }
    }

    @Override
    public int getItemCount() {
        //因为加了一个headerview
        return modelList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER_VIEW_TYPE : super.getItemViewType(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView sdvAndroidIcon;
        private TextView tvAndroidVersionName;
        private TextView tvApiLevel;
        private TextView tvAppNum;
        private TextView tvAppPercent;

        public ViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);
            if(viewType != HEADER_VIEW_TYPE){
                sdvAndroidIcon = itemView.findViewById(R.id.iv_android_logo);
                tvAndroidVersionName = itemView.findViewById(R.id.tv_android_version_name);
                tvApiLevel = itemView.findViewById(R.id.tv_api_level);
                tvAppNum = itemView.findViewById(R.id.tv_app_num);
                tvAppPercent = itemView.findViewById(R.id.tv_app_percent);
            }
        }
    }
}
