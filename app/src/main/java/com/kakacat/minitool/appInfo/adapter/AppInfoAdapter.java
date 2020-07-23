package com.kakacat.minitool.appInfo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.appInfo.model.AppInfo;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnClickListener;

import java.util.List;

public class AppInfoAdapter extends RecyclerView.Adapter<AppInfoAdapter.ViewHolder> {

    private static final int HEADER_VIEW_TYPE = 1;

    private List<AppInfo> appInfoList;
    private LayoutInflater inflater;
    private RecycleViewItemOnClickListener clickListener;

    public AppInfoAdapter(List<AppInfo> appInfoList) {
        this.appInfoList = appInfoList;
    }

    public void setOnClickListener(RecycleViewItemOnClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        if(viewType == HEADER_VIEW_TYPE){
            view = inflater.inflate(R.layout.header_app_info,parent,false);
        }else{
            view = inflater.inflate(R.layout.item_app_info,parent,false);
        }
        return new ViewHolder(view,viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position != 0){
            AppInfo appInfo = appInfoList.get(position - 1);
            holder.ivAppIcon.setImageDrawable(appInfo.getIcon());
            holder.tvAppName.setText(appInfo.getAppName());
            holder.tvPackageName.setText(appInfo.getPackageName());
            holder.tvAppVersionCode.setText(appInfo.getVersionName());
            holder.tvAppAndroidVersion.setText(appInfo.getAndroidVersionName());
            holder.tvAppApiLevel.setText(String.valueOf(appInfo.getApiLevel()));
            if(clickListener != null){
                holder.itemView.setOnClickListener(v-> clickListener.onClick(holder.itemView,position));
            }
        }

    }

    @Override
    public int getItemCount() {
        //因为有一个headerview
        return appInfoList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER_VIEW_TYPE : super.getItemViewType(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivAppIcon;
        private TextView tvAppName;
        private TextView tvPackageName;
        private TextView tvAppVersionCode;
        private TextView tvAppAndroidVersion;
        private TextView tvAppApiLevel;

        public ViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);
            if(viewType != HEADER_VIEW_TYPE){
                ivAppIcon = itemView.findViewById(R.id.iv_app_icon);
                tvAppName = itemView.findViewById(R.id.tv_app_name);
                tvPackageName = itemView.findViewById(R.id.tv_app_package_name);
                tvAppVersionCode = itemView.findViewById(R.id.tv_app_version_code);
                tvAppAndroidVersion = itemView.findViewById(R.id.tv_app_android_version);
                tvAppApiLevel = itemView.findViewById(R.id.tv_app_api_level);
            }

        }
    }

}
