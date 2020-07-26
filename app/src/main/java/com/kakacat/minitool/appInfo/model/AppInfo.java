package com.kakacat.minitool.appInfo.model;

import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.kakacat.minitool.common.util.EncryptionUtil;
import com.kakacat.minitool.common.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AppInfo implements Parcelable {

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

    private byte[] bytes;

    public AppInfo() {
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
        if(!TextUtils.isEmpty(firstInstallTime2)){
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
        if(TextUtils.isEmpty(lastUpdateTime2)){
            lastUpdateTime2 = StringUtil.getDate(lastUpdateTime);
        }
        return lastUpdateTime2;
    }

    public void setSignature(Signature signature) {
        this.signature = signature;
    }

    public int getPermissionCount(){
        return permissions.length;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public String getPermission() {
        if(permission == null){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < permissions.length; i++){
                sb.append(i).append(1).append(". ").append(permissions[i]).append("\n");
            }
            permission = sb.toString();
        }
        return permission;
    }

    public String getSignMd5() {
        if(TextUtils.isEmpty(signMd5)){
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

    private byte[] objectToBytes(Object object){
        ByteArrayOutputStream byt = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(byt);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bytes = byt.toByteArray();
        return bytes;
    }

    private Object bytesToObject(byte[] bytes){
        ByteArrayInputStream byteInt = new ByteArrayInputStream(bytes);
        ObjectInputStream ois;
        Object object = null;
        try {
            ois = new ObjectInputStream(byteInt);
            object = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByteArray(objectToBytes(this.icon));
        dest.writeString(this.appName);
        dest.writeString(this.packageName);
        dest.writeString(this.versionName);
        dest.writeString(this.androidVersionName);
        dest.writeInt(this.targetSdkVersion);
        dest.writeInt(this.minSdkVersion);
        dest.writeLong(this.firstInstallTime);
        dest.writeLong(this.lastUpdateTime);
        dest.writeString(this.firstInstallTime2);
        dest.writeString(this.lastUpdateTime2);
        dest.writeParcelable(this.signature, flags);
        dest.writeStringArray(this.permissions);
        dest.writeString(this.permission);
        dest.writeString(this.signMd5);
        dest.writeString(this.sourceDir);
    }

    protected AppInfo(Parcel in) {
        try{
            in.readByteArray(bytes);
            this.icon = (Drawable) bytesToObject(bytes);
        }catch (Exception e){
            e.printStackTrace();
        }
        this.appName = in.readString();
        this.packageName = in.readString();
        this.versionName = in.readString();
        this.androidVersionName = in.readString();
        this.targetSdkVersion = in.readInt();
        this.minSdkVersion = in.readInt();
        this.firstInstallTime = in.readLong();
        this.lastUpdateTime = in.readLong();
        this.firstInstallTime2 = in.readString();
        this.lastUpdateTime2 = in.readString();
        this.signature = in.readParcelable(Signature.class.getClassLoader());
        this.permissions = in.createStringArray();
        this.permission = in.readString();
        this.signMd5 = in.readString();
        this.sourceDir = in.readString();
    }

    public static final Parcelable.Creator<AppInfo> CREATOR = new Parcelable.Creator<AppInfo>() {
        @Override
        public AppInfo createFromParcel(Parcel source) {
            return new AppInfo(source);
        }

        @Override
        public AppInfo[] newArray(int size) {
            return new AppInfo[size];
        }
    };
}
