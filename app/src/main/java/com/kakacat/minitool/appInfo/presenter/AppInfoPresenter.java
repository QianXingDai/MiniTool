package com.kakacat.minitool.appInfo.presenter;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.kakacat.minitool.appInfo.contract.AppInfoContract;
import com.kakacat.minitool.appInfo.model.AndroidMap;
import com.kakacat.minitool.appInfo.model.ApiPercent;
import com.kakacat.minitool.appInfo.model.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bolts.Task;

public class AppInfoPresenter implements AppInfoContract.Presenter {

    public static final int SORT_BY_APP_NAME = 1;
    public static final int SORT_BY_TARGET_API = 2;
    public static final int SORT_BY_MIN_API = 3;
    public static final int SORT_BY_FIRST_INSTALL_TIME = 4;
    public static final int SORT_BY_LAST_UPDATE_TIME = 5;

    private AppInfoContract.View view;
    private PackageManager pm;

    private List<ApiPercent> apiPercentList;
    private List<PackageInfo> packageInfoList;
    private List<AppInfo> appInfoList;

    public AppInfoPresenter(AppInfoContract.View view) {
        this.view = view;
        this.pm = view.getContext().getPackageManager();
    }

    @Override
    public void initData() {
        Task.callInBackground(() -> {
            initApiPercentData();
            initAppInfoData();
            return null;
        }).continueWith(task -> {
            view.onUpdateDataSet();
            return null;
        },Task.UI_THREAD_EXECUTOR);
    }

    private void initApiPercentData(){
        apiPercentList = getApiPercentList();
        packageInfoList = getPackageInfoList();
        int[] apiCount = new int[AndroidMap.icons.length];

        packageInfoList.forEach(packageInfo -> apiCount[packageInfo.applicationInfo.targetSdkVersion - 1]++);
        for (int i = apiCount.length - 1; i >= 0; i--) {
            if(apiCount[i] != 0){
                String s = String.valueOf(apiCount[i] * 100.0 / packageInfoList.size());
                s = s.substring(0, s.indexOf('.') + 2) + "%";
                ApiPercent model = new ApiPercent(AndroidMap.icons[i],AndroidMap.versionName[i],"API " + i,apiCount[i],s);
                apiPercentList.add(model);
            }
        }
    }

    private void initAppInfoData(){
        appInfoList = getAppInfoList();
        packageInfoList = getPackageInfoList();

        packageInfoList.forEach(packageInfo -> {
            AppInfo appInfo = new AppInfo();

            appInfo.setTargetSdkVersion(packageInfo.applicationInfo.targetSdkVersion);
            appInfo.setIcon(packageInfo.applicationInfo.loadIcon(pm));
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setVersionName(packageInfo.versionName);
            appInfo.setAndroidVersionName(AndroidMap.versionName[appInfo.getTargetSdkVersion() - 1]);
            appInfo.setMinSdkVersion(packageInfo.applicationInfo.minSdkVersion);
            appInfo.setFirstInstallTime(packageInfo.firstInstallTime);
            appInfo.setLastUpdateTime(packageInfo.lastUpdateTime);
            appInfo.setSignature(packageInfo.signatures[0]);
            appInfo.setPermissions(packageInfo.requestedPermissions);
            appInfo.setSourceDir(packageInfo.applicationInfo.sourceDir);

            appInfoList.add(appInfo);
        });
    }

    @Override
    public List<ApiPercent> getApiPercentList() {
        if(apiPercentList == null){
            apiPercentList = new ArrayList<>();
        }
        return apiPercentList;
    }

    @Override
    public List<PackageInfo> getPackageInfoList() {
        if(packageInfoList == null){
            packageInfoList = pm.getInstalledPackages(PackageManager.GET_SIGNATURES|PackageManager.GET_PERMISSIONS);
        }
        return packageInfoList;
    }

    @Override
    public List<AppInfo> getAppInfoList(){
        if(appInfoList == null){
            appInfoList = new ArrayList<>();
        }
        return appInfoList;
    }

    @Override
    public void sortAppInfoList(int sortFlag) {
        Collections.sort(appInfoList, (o1, o2) -> {
            if(sortFlag == SORT_BY_APP_NAME){
                return o2.getAppName().compareTo(o1.getAppName());
            }else if(sortFlag == SORT_BY_TARGET_API){
                return o2.getTargetSdkVersion()- o1.getTargetSdkVersion();
            }else if(sortFlag == SORT_BY_MIN_API){
                return o2.getMinSdkVersion() - o1.getMinSdkVersion();
            }else if(sortFlag == SORT_BY_FIRST_INSTALL_TIME){
                return (int) (o2.getFirstInstallTime() - o1.getFirstInstallTime());
            }else if(sortFlag == SORT_BY_LAST_UPDATE_TIME){
                return (int) (o2.getLastUpdateTime() - o1.getLastUpdateTime());
            }else{
                return 0;
            }
        });

        view.onUpdateAppInfoDataSet();
    }
}
