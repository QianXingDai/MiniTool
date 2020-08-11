package com.kakacat.minitool.wifipasswordview;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.BaseActivity;
import com.kakacat.minitool.common.util.UiUtil;

public class WifiPwdActivity extends BaseActivity implements Contract.View {

    private Contract.Presenter presenter;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_pwd_view);

        initView();
        initData();
    }

    @Override
    public void initData() {
        presenter.initData();
    }

    @Override
    public void initView() {
        UiUtil.setTranslucentStatusBarWhite(this);
        UiUtil.initToolbar(this,true);

        presenter = new Presenter(this);

        adapter = new Adapter(presenter.getWifiList());
        RecyclerView recyclerView = findViewById(R.id.rv_wifi);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter.setLongClickListener((v, position) -> presenter.copyToClipboard(position));
    }

    @Override
    public void onCopyCallback(String result) {
        UiUtil.showToast(this, result);
    }

    @Override
    public void onGetWifiDataCallBack(String result) {
        UiUtil.showToast(this, result);
        adapter.notifyDataSetChanged();
    }
}
