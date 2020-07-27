package com.kakacat.minitool.wifipasswordview;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.UiUtil;
import com.kakacat.minitool.common.util.SystemUtil;

public class WifiPwdActivity extends AppCompatActivity implements Contract.View {

    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_pwd_view);

        initData();
        initView();
    }

    @Override
    public void initData(){
        setPresenter(new Presenter(this));
        presenter.initData();
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView() {
        initToolbar();

        MyAdapter myAdapter = new MyAdapter(presenter.getWifiList());
        RecyclerView recyclerView = findViewById(R.id.rv_wifi);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        myAdapter.setLongClickListener((v, position) -> {
            CharSequence wifiName = ((TextView)v.findViewById(R.id.tv_wifi_name)).getText();
            CharSequence pwd = ((TextView)v.findViewById(R.id.tv_wifi_pwd)).getText();
            SystemUtil.copyToClipboard(this,"wifiPwd",pwd);
            UiUtil.showSnackBar(recyclerView,"\"" + wifiName + "\"的wifi密码已复制");
        });
    }

    private void initToolbar(){
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void onGetWifiDataCallBack(String result) {
        UiUtil.showToast(this,result);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
