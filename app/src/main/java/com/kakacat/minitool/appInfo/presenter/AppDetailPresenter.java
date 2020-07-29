package com.kakacat.minitool.appInfo.presenter;

import android.graphics.Bitmap;

import com.kakacat.minitool.appInfo.activity.AppDetailActivity;
import com.kakacat.minitool.appInfo.contract.AppDetailContract;
import com.kakacat.minitool.appInfo.model.AppInfo;
import com.kakacat.minitool.common.util.UiUtil;
import com.kakacat.minitool.common.util.SystemUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AppDetailPresenter implements AppDetailContract.Presenter {

    private AppDetailContract.View view;
    private AppDetailActivity activity;

    private AppInfo appInfo;

    public AppDetailPresenter(AppDetailContract.View view) {
        this.view = view;
        this.activity = view.getActivity();
    }

    @Override
    public void initData(){
        appInfo = activity.getIntent().getParcelableExtra("appInfo");
    }

    @Override
    public void saveIcon(){
        try{
            String path = "/storage/emulated/0/MiniTool/" + appInfo.getAppName() + ".png";
            File file = new File(path);
            String result;
            if(!file.exists()){
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                Bitmap bitmap = UiUtil.drawableToBitmap(appInfo.getIcon());
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
                fos.flush();
                fos.close();
                result = "成功保存在目录 : " + path;
            }
            else{
                result = "已经保存过该图片!";
            }
            view.onSaveIconResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openMarket() {
        SystemUtil.openMarket(activity);
    }

    @Override
    public void saveApk() {
        String srcPath = appInfo.getSourceDir();
        String desPath = "/storage/emulated/0/MiniTool/";
        String[] commands = new String[]{
                "cp " + srcPath + " " + desPath + "\n",
        };
        SystemUtil.executeLinuxCommand(commands,false,false);
        view.onSaveApkResult("提取成功 保存在" + desPath);
    }

    @Override
    public void openDetailInSetting() {
        SystemUtil.openAppDetailInSetting(activity);
    }

    @Override
    public void copyMd5() {
        SystemUtil.copyToClipboard(activity,"md5",appInfo.getSignMd5());
        view.onCopyMd5Result("复制成功");
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }
}
