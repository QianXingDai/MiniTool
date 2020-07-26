package com.kakacat.minitool.appInfo.model;

public class ApiPercent {
    private int iconId;
    private String versionName;
    private String versionApi;
    private int appNum;
    private String appPercent;

    public ApiPercent(int iconId, String versionName, String versionApi, int appNum, String appPercent) {
        this.iconId = iconId;
        this.versionName = versionName;
        this.versionApi = versionApi;
        this.appNum = appNum;
        this.appPercent = appPercent;
    }

    public int getIconId() {
        return iconId;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getVersionApi() {
        return versionApi;
    }

    public int getAppNum() {
        return appNum;
    }

    public String getAppPercent() {
        return appPercent;
    }
}
