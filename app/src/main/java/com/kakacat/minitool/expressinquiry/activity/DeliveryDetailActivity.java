package com.kakacat.minitool.expressinquiry.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.kakacat.minitool.R;
import com.kakacat.minitool.expressinquiry.adapter.DeliveryDetailAdapter;
import com.kakacat.minitool.expressinquiry.model.Delivery;

public class DeliveryDetailActivity extends AppCompatActivity {

    private Delivery mDelivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_detail);

        initView();
    }

    private void initView() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);

        mDelivery = (Delivery) getIntent().getSerializableExtra("delivery");
        initToolbar();

        RecyclerView recyclerView = findViewById(R.id.rv);

        assert mDelivery != null;
        DeliveryDetailAdapter adapter = new DeliveryDetailAdapter(mDelivery.getPackageDetailList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar));
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(mDelivery.getCompanyName());
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}