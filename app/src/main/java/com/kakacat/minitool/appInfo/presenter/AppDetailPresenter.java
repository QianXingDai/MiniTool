package com.kakacat.minitool.appInfo.presenter;

import com.kakacat.minitool.appInfo.activity.AppDetailActivity;
import com.kakacat.minitool.appInfo.contract.AppDetailContract;
import com.kakacat.minitool.appInfo.model.AppDetailModel;
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean;

import bolts.Continuation;
import bolts.Task;

public class AppDetailPresenter implements AppDetailContract.Presenter {

    private AppDetailContract.View view;
    private AppDetailModel model;
    private AppDetailActivity activity;

    public AppDetailPresenter(AppDetailContract.View view) {
        this.view = view;
        this.model = AppDetailModel.getInstance();
        this.activity = view.getActivity();
    }

    @Override
    public void initData() {
        model.getAppInfoBean(activity);
    }

    @Override
    public void saveIcon() {
        Task.callInBackground(() -> model.saveIcon()).onSuccess((Continuation<String, Void>) task -> {
            view.onSaveIconResult(task.getResult());
            return null;
        }, Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public void openMarket() {
        model.openMarket(activity);
    }

    @Override
    public void saveApk() {
        Task.callInBackground(() -> model.saveApk()).onSuccess((Continuation<String, Void>) task -> {
            view.onSaveApkResult(task.getResult());
            return null;
        }, Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public void openDetailInSetting() {
        model.openDetailInSetting(activity);
    }

    @Override
    public void copyMd5() {
        view.onCopyMd5Result(model.copyMd5(activity));
    }

    @Override
    public AppInfoBean getAppInfoBean() {
        return model.getAppInfoBean(activity);
    }
}
