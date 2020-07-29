package com.kakacat.minitool.appInfo.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.appInfo.adapter.ApiPercentAdapter;
import com.kakacat.minitool.appInfo.adapter.AppInfoAdapter;
import com.kakacat.minitool.appInfo.contract.AppInfoContract;
import com.kakacat.minitool.appInfo.presenter.AppInfoPresenter;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.common.util.UiUtil;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;


public class AppInfoActivity extends FrescoInitActivity implements AppInfoContract.View,View.OnClickListener {

    private AppInfoContract.Presenter presenter;

    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private MyPopupWindow sortDialog;

    private ApiPercentAdapter apiPercentAdapter;
    private AppInfoAdapter appInfoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);

        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }

    @Override
    public void initData() {
        presenter.initData();
    }

    @Override
    public void initView() {
        setPresenter(new AppInfoPresenter(this));
        initToolbar();

        nestedScrollView = findViewById(R.id.nested_scroll_view);
        linearLayout = findViewById(R.id.linear_layout);
        progressBar = findViewById(R.id.progress_bar);

        initApiPercentView();
        initAppInfoView();
    }

    @Override
    public void onUpdateDataSet(){
        apiPercentAdapter.notifyDataSetChanged();
        appInfoAdapter.notifyDataSetChanged();
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onUpdateAppInfoDataSet(){
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        appInfoAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.VISIBLE);
        sortDialog.dismiss();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initToolbar(){
        super.initToolbar();

        TextView tvAndroidVersion = findViewById(R.id.tv_android_version);
        TextView tvSecurityPatchLevel = findViewById(R.id.tv_security_patch_level);
        TextView tvDeviceName = findViewById(R.id.tv_device_name);

        tvAndroidVersion.setText("Android " + Build.VERSION.RELEASE);
        tvSecurityPatchLevel.setText(Build.VERSION.SECURITY_PATCH);
        tvDeviceName.setText(Build.DEVICE);
    }

    private void initApiPercentView(){
        RecyclerView rvApiPercent = findViewById(R.id.rv_api_percent);
        apiPercentAdapter = new ApiPercentAdapter(presenter.getApiPercentList());
        rvApiPercent.setAdapter(apiPercentAdapter);
        rvApiPercent.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initAppInfoView(){
        RecyclerView rvAppInfo = findViewById(R.id.rv_app_info);
        appInfoAdapter = new AppInfoAdapter(presenter.getAppInfoList());
        rvAppInfo.setAdapter(appInfoAdapter);
        rvAppInfo.setLayoutManager(new LinearLayoutManager(this));

/*      TODO:这里序列化传输有问题，暂时屏蔽掉，以后再改
        appInfoAdapter.setOnClickListener((v, position) -> {
            Intent intent = new Intent(AppInfoActivity.this,AppDetailActivity.class);
            intent.putExtra("appInfo",presenter.getAppInfoList().get(position));
            startActivity(intent);
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


    @Override
    public void setPresenter(AppInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:{
                slideUpToTop();
                break;
            }
            case R.id.iv_sort:{
                showSortDialog();
                break;
            }
            case R.id.rb_sort_by_app_name:{
                presenter.sortAppInfoList(AppInfoPresenter.SORT_BY_APP_NAME);
                break;
            }
            case R.id.rb_sort_by_target_api:{
                presenter.sortAppInfoList(AppInfoPresenter.SORT_BY_TARGET_API);
                break;
            }
            case R.id.rb_sort_by_min_api:{
                presenter.sortAppInfoList(AppInfoPresenter.SORT_BY_MIN_API);
                break;
            }
            case R.id.rb_sort_by_first_install_time:{
                presenter.sortAppInfoList(AppInfoPresenter.SORT_BY_FIRST_INSTALL_TIME);
                break;
            }
            case R.id.rb_sort_by_last_update_time:{
                presenter.sortAppInfoList(AppInfoPresenter.SORT_BY_LAST_UPDATE_TIME);
                break;
            }
        }
    }

    @Override
    public void slideUpToTop(){
        UiUtil.slideUpToTop(nestedScrollView);
    }

    @Override
    public void showSortDialog(){
        if(sortDialog == null){
            View contentView = LayoutInflater.from(this).inflate(R.layout.sort_dialog_layout,nestedScrollView,false);
            sortDialog = new MyPopupWindow(this,contentView,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        sortDialog.showAtLocation(nestedScrollView, Gravity.CENTER, 0, 0);
    }
}
