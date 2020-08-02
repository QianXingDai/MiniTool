package com.kakacat.minitool.appInfo.model.bean;

import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.kakacat.minitool.common.util.EncryptionUtil;
import com.kakacat.minitool.common.util.StringUtil;

import java.io.Serializable;

public class AppInfoBean implements Serializable {

    private Drawable icon;
    private String appName;
    private String packageName;
    private String versionName;
    private String androidVersionName;
    private int targetSdkVersion;
    private int minSdkVersion;
    private long firstInstallTime;
    private long lastUpdateTime;
    //这个是转换成yyyy-mm这种形式的，下同
    private String firstInstallTime2;
    private String lastUpdateTime2;
    private Signature signature;
    private String[] permissions;
    //上面的permissions整合后的
    private String permission;
    private String signMd5;
    private String sourceDir;

    public AppInfoBean() {
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getAndroidVersionName() {
        return androidVersionName;
    }

    public void setAndroidVersionName(String androidVersionName) {
        this.androidVersionName = androidVersionName;
    }

    public int getTargetSdkVersion() {
        return targetSdkVersion;
    }

    public void setTargetSdkVersion(int targetSdkVersion) {
        this.targetSdkVersion = targetSdkVersion;
    }

    public int getMinSdkVersion() {
        return minSdkVersion;
    }

    public void setMinSdkVersion(int minSdkVersion) {
        this.minSdkVersion = minSdkVersion;
    }

    public long getFirstInstallTime() {
        return firstInstallTime;
    }

    public void setFirstInstallTime(long firstInstallTime) {
        this.firstInstallTime = firstInstallTime;
    }

    public String getFirstInstallTime2() {
        if (!TextUtils.isEmpty(firstInstallTime2)) {
            firstInstallTime2 = StringUtil.getDate(firstInstallTime);
        }
        return firstInstallTime2;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getLastUpdateTime2() {
        if (TextUtils.isEmpty(lastUpdateTime2)) {
            lastUpdateTime2 = StringUtil.getDate(lastUpdateTime);
        }
        return lastUpdateTime2;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public int getPermissionCount() {
        return permissions.length;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public String getPermission() {
        if (permission == null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < permissions.length; i++) {
                sb.append(i).append(1).append(". ").append(permissions[i]).append("\n");
            }
            permission = sb.toString();
        }
        return permission;
    }

    public String getSignMd5() {
        if (TextUtils.isEmpty(signMd5)) {
            signMd5 = EncryptionUtil.getSignMd5Str(signature).toUpperCase();
        }
        return signMd5;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }
}
