package com.kakacat.minitool.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.myinterface.RecycleViewItemOnClickListener;
import com.kakacat.minitool.common.ui.ItemDecoration;
import com.kakacat.minitool.main.adapter.MainAdapter;
import com.kakacat.minitool.main.model.MainItem;

import java.util.List;

public class MyFragment extends Fragment {

    Context context;
    private MainAdapter adapter;

    public MyFragment() {

    }

    public MyFragment(List<MainItem> itemList) {
        adapter = new MainAdapter(itemList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (context == null) {
            context = getContext();
        }
        View view = inflater.inflate(R.layout.main_fragment_layout, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new ItemDecoration(30, 30));

        return view;
    }

    public void setOnClickListener(RecycleViewItemOnClickListener clickListener) {
        adapter.setOnClickListener(clickListener);
    }
}
