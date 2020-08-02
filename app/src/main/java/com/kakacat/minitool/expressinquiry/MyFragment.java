package com.kakacat.minitool.expressinquiry;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.expressinquiry.activity.DeliveryDetailActivity;
import com.kakacat.minitool.expressinquiry.adapter.DeliveryAdapter;
import com.kakacat.minitool.expressinquiry.model.Delivery;

import java.util.List;
import java.util.Objects;

public class MyFragment extends Fragment {

    private List<Delivery> deliveryList;
    private DeliveryAdapter adapter;
    private Intent intent;

    public MyFragment(List<Delivery> deliveryList) {
        this.deliveryList = deliveryList;
        adapter = new DeliveryAdapter(deliveryList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setItemListener((v, position) -> startDetailActivity(deliveryList.get(position)));
        return view;
    }

    private void startDetailActivity(Delivery delivery) {
        if (intent == null) {
            intent = new Intent();
            intent.setClass(Objects.requireNonNull(getContext()), DeliveryDetailActivity.class);
        }
        intent.removeExtra("delivery");
        intent.putExtra("delivery", delivery);
        startActivity(intent);
    }

    public DeliveryAdapter getAdapter() {
        return adapter;
    }
}
