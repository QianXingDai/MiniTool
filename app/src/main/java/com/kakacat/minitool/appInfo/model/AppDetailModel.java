package com.kakacat.minitool.appInfo.model;

import android.app.Activity;
import android.graphics.Bitmap;

import com.kakacat.minitool.appInfo.model.bean.AppInfoBean;
import com.kakacat.minitool.common.util.SystemUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AppDetailModel {

    private static AppDetailModel model;

    private AppInfoBean appInfoBean;

    private AppDetailModel() {

    }

    public static AppDetailModel getInstance(){
        if(model == null){
            model = new AppDetailModel();
        }
        return model;
    }

    public AppInfoBean getAppInfoBean(Activity activity) {
        if (appInfoBean == null) {
            appInfoBean = activity.getIntent().getParcelableExtra("appInfo");
        }
        return appInfoBean;
    }

    public String saveIcon() {
        String result = null;
        try {
            String path = "/storage/emulated/0/MiniTool/" + appInfoBean.getAppName() + ".png";
            File file = new File(path);
            SystemUtil.createFile(file,false);

            FileOutputStream fos = new FileOutputStream(file);
            appInfoBean.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            result = "成功保存在目录 : " + path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void openMarket(Activity activity) {
        SystemUtil.openMarket(activity);
    }

    public String saveApk() {
        String srcPath = appInfoBean.getSourceDir();
        String desPath = "/storage/emulated/0/MiniTool/" + appInfoBean.getAppName() + ".apk";
        SystemUtil.createFile(new File(desPath),false);
        String[] commands = new String[]{
                "cp " + srcPath + " " + desPath + "\n",
        };
        SystemUtil.executeLinuxCommand(commands, false, true);
        return "提取成功 保存在" + desPath;
    }

    public String copyMd5(Activity activity) {
        SystemUtil.copyToClipboard(activity, "md5", appInfoBean.getSignMd5());
        return "复制成功";
    }

    public void openDetailInSetting(Activity activity) {
        SystemUtil.openAppDetailInSetting(activity,appInfoBean.getPackageName());
    }
}
