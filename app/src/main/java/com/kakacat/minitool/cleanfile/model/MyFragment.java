package com.kakacat.minitool.cleanfile.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.cleanfile.adapter.FileAdapter;

import java.util.List;

public class MyFragment extends Fragment {

    private FileAdapter adapter;
    private List<FileItem> fileItemList;
    private boolean isSelectedAll;

    public MyFragment(List<FileItem> fileItemList) {
        this.fileItemList = fileItemList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_layout,container,false);
        RecyclerView rv = view.findViewById(R.id.rv_file);
        adapter = new FileAdapter(fileItemList);
        adapter.setOnClickListener((v, position) -> {
            FileItem fileItem = fileItemList.get(position);
            CheckBox checkBox = v.findViewById(R.id.cb_selected);
            if(fileItem.getChecked()){
                checkBox.setChecked(false);
                fileItem.setChecked(false);
            }else{
                checkBox.setChecked(true);
                fileItem.setChecked(true);
            }
        });
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public FileAdapter getAdapter() {
        return adapter;
    }

    public boolean isSelectedAll() {
        return isSelectedAll;
    }

    public void setSelectedAll(boolean selectedAll, AppCompatImageView btn) {
        isSelectedAll = selectedAll;
        if(isSelectedAll()){
            btn.setBackgroundResource(R.drawable.ic_clear);
        }else{
            btn.setBackgroundResource(R.drawable.ic_select_all);
        }
    }
}
