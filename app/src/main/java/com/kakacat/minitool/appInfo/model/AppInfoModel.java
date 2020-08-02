package com.kakacat.minitool.appInfo.model;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.kakacat.minitool.R;
import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean;
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppInfoModel {

    public static final int SORT_BY_APP_NAME = 1;
    public static final int SORT_BY_TARGET_API = 2;
    public static final int SORT_BY_MIN_API = 3;
    public static final int SORT_BY_FIRST_INSTALL_TIME = 4;
    public static final int SORT_BY_LAST_UPDATE_TIME = 5;

    // API对应关系
    private static String[] versionName = new String[]
            {"1.0", "1.1", "Cupcake", "Donut", "Eclair",
                    "Eclair", "Eclair", "Froyo", "Gingerbread", "Gingerbread",
                    "Honeycomb", "Honeycomb", "Honeycomb", "Ice Cream Sandwich", "Ice Cream Sandwich",
                    "Jelly Bean", "Jelly Bean", "Jelly Bean", "KitKat", "KitKat",
                    "Lollipop", "Lollipop", "Marshmallow", "Nougat", "Nougat",
                    "Oreo", "Oreo", "Pie", "Android Q", "Android R"};


    private static int[] icons = new int[]
            {R.drawable.android_cupcake, R.drawable.android_cupcake, R.drawable.android_cupcake, R.drawable.android_donut, R.drawable.android_eclair,
                    R.drawable.android_eclair, R.drawable.android_eclair, R.drawable.android_froyo, R.drawable.android_gingerbread, R.drawable.android_gingerbread,
                    R.drawable.android_honeycomb, R.drawable.android_honeycomb, R.drawable.android_honeycomb, R.drawable.android_ice_cream_sandwich, R.drawable.android_ice_cream_sandwich,
                    R.drawable.android_jelly_bean, R.drawable.android_jelly_bean, R.drawable.android_jelly_bean, R.drawable.android_kitkat, R.drawable.android_kitkat,
                    R.drawable.android_lollipop, R.drawable.android_lollipop, R.drawable.android_marshmallow, R.drawable.android_nougat, R.drawable.android_nougat,
                    R.drawable.android_oreo, R.drawable.android_oreo, R.drawable.android_pie, R.drawable.android_q, R.drawable.android_r};

    private static AppInfoModel model;

    private List<ApiPercentBean> apiPercentBeanList;
    private List<PackageInfo> packageInfoList;
    private List<AppInfoBean> appInfoBeanList;

    private PackageManager pm;

    private AppInfoModel() {

    }

    public static AppInfoModel getModelInstance() {
        if (model == null) {
            model = new AppInfoModel();
        }
        return model;
    }

    public void initData(PackageManager pm) {
        this.pm = pm;
        initApiPercentData();
        initAppInfoData();
    }

    private void initApiPercentData() {
        apiPercentBeanList = getApiPercentBeanList();
        packageInfoList = getPackageInfoList();
        int[] apiCount = new int[icons.length];

        packageInfoList.forEach(packageInfo -> apiCount[packageInfo.applicationInfo.targetSdkVersion - 1]++);
        for (int i = apiCount.length - 1; i >= 0; i--) {
            if (apiCount[i] != 0) {
                String s = String.valueOf(apiCount[i] * 100.0 / packageInfoList.size());
                s = s.substring(0, s.indexOf('.') + 2) + "%";
                ApiPercentBean model = new ApiPercentBean(icons[i], versionName[i], "API " + i, apiCount[i], s);
                apiPercentBeanList.add(model);
            }
        }
    }

    private void initAppInfoData() {
        appInfoBeanList = getAppInfoBeanList();
        packageInfoList = getPackageInfoList();

        packageInfoList.forEach(packageInfo -> {
            AppInfoBean appInfoBean = new AppInfoBean();

            appInfoBean.setTargetSdkVersion(packageInfo.applicationInfo.targetSdkVersion);
            appInfoBean.setIcon(packageInfo.applicationInfo.loadIcon(pm));
            appInfoBean.setAppName(packageInfo.applicationInfo.loadLabel(pm).toString());
            appInfoBean.setPackageName(packageInfo.packageName);
            appInfoBean.setVersionName(packageInfo.versionName);
            appInfoBean.setAndroidVersionName(versionName[appInfoBean.getTargetSdkVersion() - 1]);
            appInfoBean.setMinSdkVersion(packageInfo.applicationInfo.minSdkVersion);
            appInfoBean.setFirstInstallTime(packageInfo.firstInstallTime);
            appInfoBean.setLastUpdateTime(packageInfo.lastUpdateTime);
            appInfoBean.setSignature(packageInfo.signatures[0]);
            appInfoBean.setPermissions(packageInfo.requestedPermissions);
            appInfoBean.setSourceDir(packageInfo.applicationInfo.sourceDir);

            appInfoBeanList.add(appInfoBean);
        });
    }

    public List<ApiPercentBean> getApiPercentBeanList() {
        if (apiPercentBeanList == null) {
            apiPercentBeanList = new ArrayList<>();
        }
        return apiPercentBeanList;
    }

    public List<PackageInfo> getPackageInfoList() {
        if (packageInfoList == null) {
            packageInfoList = pm.getInstalledPackages(PackageManager.GET_SIGNATURES | PackageManager.GET_PERMISSIONS);
        }
        return packageInfoList;
    }

    public List<AppInfoBean> getAppInfoBeanList() {
        if (appInfoBeanList == null) {
            appInfoBeanList = new ArrayList<>();
        }
        return appInfoBeanList;
    }

    public void sortAppInfoList(int sortFlag) {
        Collections.sort(appInfoBeanList, (o1, o2) -> {
            if (sortFlag == SORT_BY_APP_NAME) {
                return o2.getAppName().compareTo(o1.getAppName());
            } else if (sortFlag == SORT_BY_TARGET_API) {
                return o2.getTargetSdkVersion() - o1.getTargetSdkVersion();
            } else if (sortFlag == SORT_BY_MIN_API) {
                return o2.getMinSdkVersion() - o1.getMinSdkVersion();
            } else if (sortFlag == SORT_BY_FIRST_INSTALL_TIME) {
                return (int) (o2.getFirstInstallTime() - o1.getFirstInstallTime());
            } else if (sortFlag == SORT_BY_LAST_UPDATE_TIME) {
                return (int) (o2.getLastUpdateTime() - o1.getLastUpdateTime());
            } else {
                return 0;
            }
        });
    }
}
