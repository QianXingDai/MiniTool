package com.kakacat.minitool.appInfo.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.kakacat.minitool.R;
import com.kakacat.minitool.appInfo.contract.AppDetailContract;
import com.kakacat.minitool.appInfo.model.AppInfo;
import com.kakacat.minitool.appInfo.presenter.AppDetailPresenter;
import com.kakacat.minitool.common.ui.UiUtil;

public class AppDetailActivity extends AppCompatActivity implements AppDetailContract.View,View.OnClickListener{

    private static final int SAVE_ICON = 1;
    private static final int SAVE_APK = 2;

    private AppDetailContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);

        initData();
        initView();
    }

    @Override
    public void initData() {
        setPresenter(new AppDetailPresenter(this));
        presenter.initData();
    }

    @Override
    public void setPresenter(AppDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TextView tvAppName = findViewById(R.id.tv_app_name);
        TextView tvPackageName = findViewById(R.id.tv_package_name);
        TextView tvVersionName = findViewById(R.id.tv_version_name);
        TextView tvFirstInstallTime = findViewById(R.id.tv_first_install_time);
        TextView tvLastUpdateTime = findViewById(R.id.tv_last_update_time);
        TextView tvTargetApi = findViewById(R.id.tv_target_api);
        TextView tvMinApi = findViewById(R.id.tv_min_api);
        TextView tvMd5Signature = findViewById(R.id.tv_md5_sign);
        TextView tvPermission = findViewById(R.id.tv_permission);
        TextView tvHeader3 = findViewById(R.id.tv_header3);

        ImageView ivAppIcon = findViewById(R.id.iv_app_icon);

        AppInfo appInfo = presenter.getAppInfo();

        tvAppName.setText(appInfo.getAppName());
        tvPackageName.setText(appInfo.getPackageName());
        tvVersionName.setText(appInfo.getVersionName());
        tvFirstInstallTime.setText(appInfo.getFirstInstallTime2());
        tvLastUpdateTime.setText(appInfo.getLastUpdateTime2());
        tvTargetApi.setText(String.valueOf(appInfo.getTargetSdkVersion()));
        tvMinApi.setText(String.valueOf(appInfo.getMinSdkVersion()));
        tvMd5Signature.setText(appInfo.getSignMd5());
        tvHeader3.setText("权限声明" + "(" + appInfo.getPermissionCount() + "个)");
        tvPermission.setText(appInfo.getPermission());

        ivAppIcon.setImageDrawable(appInfo.getIcon());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_save_icon : {
                saveIcon();
                break;
            }
            case R.id.bt_open_market : {
                presenter.openMarket();
                break;
            }
            case R.id.bt_get_apk : {
                saveApk();
                break;
            }
            case R.id.bt_open_detail : {
                presenter.openDetailInSetting();
                break;
            }
            case R.id.bt_copy_md5 : {
                presenter.copyMd5();
                break;
            }
        }
    }


    @Override
    public void saveIcon(){
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ActivityCompat.checkSelfPermission(this,permissions[0]) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions,SAVE_ICON);
        }else{
            presenter.saveIcon();
        }
    }

    @Override
    public void onSaveIconResult(String result){
        UiUtil.showToast(this,result);
    }

    @Override
    public void saveApk() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(ActivityCompat.checkSelfPermission(this,permissions[0]) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions,SAVE_APK);
        }else{
            presenter.saveApk();
        }
    }

    @Override
    public void onCopyMd5Result(String result){
        UiUtil.showToast(this,result);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if(requestCode == SAVE_ICON){
                presenter.saveIcon();
            }else if(requestCode == SAVE_APK){
                presenter.saveApk();
            }
        }else{
            UiUtil.showToast(this,"获取存储权限失败");
        }
    }

    @Override
    public void onSaveApkResult(String result){
        UiUtil.showToast(this,result);
    }

    @Override
    public AppDetailActivity getActivity() {
        return this;
    }
}
