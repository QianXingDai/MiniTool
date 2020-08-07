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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.kakacat.minitool.R;
import com.kakacat.minitool.appInfo.contract.AppDetailContract;
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean;
import com.kakacat.minitool.appInfo.presenter.AppDetailPresenter;
import com.kakacat.minitool.common.util.UiUtil;

public class AppDetailActivity extends AppCompatActivity implements AppDetailContract.View, View.OnClickListener {

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
        getPresenter().initData();
    }

    @Override
    public AppDetailContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new AppDetailPresenter(this);
        }
        return presenter;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initView() {
        UiUtil.initToolbar(this,true);

        AppInfoBean appInfoBean = getPresenter().getAppInfoBean();
        ((TextView)findViewById(R.id.tv_app_name)).setText(appInfoBean.getAppName());
        ((TextView)findViewById(R.id.tv_package_name)).setText(appInfoBean.getPackageName());
        ((TextView)findViewById(R.id.tv_version_name)).setText(appInfoBean.getVersionName());
        ((TextView)findViewById(R.id.tv_first_install_time)).setText(appInfoBean.getFirstInstallTime2());
        ((TextView)findViewById(R.id.tv_last_update_time)).setText(appInfoBean.getLastUpdateTime2());
        ((TextView)findViewById(R.id.tv_target_api)).setText(String.valueOf(appInfoBean.getTargetSdkVersion()));
        ((TextView)findViewById(R.id.tv_min_api)).setText(String.valueOf(appInfoBean.getMinSdkVersion()));
        ((TextView)findViewById(R.id.tv_md5_sign)).setText(appInfoBean.getSignMd5());
        ((TextView)findViewById(R.id.tv_permission)).setText(appInfoBean.getPermission());
        ((TextView)findViewById(R.id.tv_header3)).setText("权限声明" + "(" + appInfoBean.getPermissionCount() + "个)");
        ((ImageView)findViewById(R.id.iv_app_icon)).setImageBitmap(appInfoBean.getBitmap());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_save_icon: {
                saveIcon();
                break;
            }
            case R.id.bt_open_market: {
                getPresenter().openMarket();
                break;
            }
            case R.id.bt_get_apk: {
                saveApk();
                break;
            }
            case R.id.bt_open_detail: {
                getPresenter().openDetailInSetting();
                break;
            }
            case R.id.bt_copy_md5: {
                getPresenter().copyMd5();
                break;
            }
        }
    }


    @Override
    public void saveIcon() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, SAVE_ICON);
        } else {
            getPresenter().saveIcon();
        }
    }

    @Override
    public void onSaveIconResult(String result) {
        UiUtil.showToast(this, result);
    }

    @Override
    public void saveApk() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, SAVE_APK);
        } else {
            getPresenter().saveApk();
        }
    }

    @Override
    public void onCopyMd5Result(String result) {
        UiUtil.showToast(this, result);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == SAVE_ICON) {
                getPresenter().saveIcon();
            } else if (requestCode == SAVE_APK) {
                getPresenter().saveApk();
            }
        } else {
            UiUtil.showToast(this, "获取存储权限失败");
        }
    }

    @Override
    public void onSaveApkResult(String result) {
        UiUtil.showToast(this, result);
    }

    @Override
    public AppDetailActivity getActivity() {
        return this;
    }
}
