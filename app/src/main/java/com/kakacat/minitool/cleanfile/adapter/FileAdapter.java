package com.kakacat.minitool.cleanfile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.kakacat.minitool.R;
import com.kakacat.minitool.cleanfile.model.FileItem;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnClickListener;
import com.kakacat.minitool.common.util.StringUtil;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> {

    private List<FileItem> fileList;
    private LayoutInflater inflater;
    private RecycleViewItemOnClickListener onClickListener;

    public FileAdapter(List<FileItem> fileList) {
        this.fileList = fileList;
    }

    public void setOnClickListener(RecycleViewItemOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public FileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        View view = inflater.inflate(R.layout.file_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FileItem fileItem = fileList.get(position);
        String result = StringUtil.byteToMegabyte(fileItem.getFile().length());

        holder.tvFileSize.setText(result);
        holder.tvFileName.setText(fileItem.getFile().getName());
        holder.tvFilePath.setText(fileItem.getFile().getAbsolutePath());
        holder.checkBox.setChecked(fileItem.getChecked());
        holder.btFileDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 这次懒得写..下次再写这里...
                 *
                 * */
            }
        });

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(v -> onClickListener.onClick(holder.itemView, position));
        }
    }


    @Override
    public int getItemCount() {
        return fileList.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFileSize;
        private TextView tvFileName;
        private TextView tvFilePath;
        private CheckBox checkBox;
        private MaterialButton btFileDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileSize = itemView.findViewById(R.id.tv_head);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            tvFilePath = itemView.findViewById(R.id.tv_file_path);
            checkBox = itemView.findViewById(R.id.cb_selected);
            btFileDetail = itemView.findViewById(R.id.bt_file_detail);
        }
    }
}
