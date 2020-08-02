package com.kakacat.minitool.appInfo.presenter;

import com.kakacat.minitool.appInfo.contract.AppInfoContract;
import com.kakacat.minitool.appInfo.model.AppInfoModel;
import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean;
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean;
import com.kakacat.minitool.common.util.ThreadUtil;

import java.util.List;

public class AppInfoPresenter implements AppInfoContract.Presenter {

    private AppInfoContract.View view;
    private AppInfoModel appInfoModel;

    public AppInfoPresenter(AppInfoContract.View view) {
        this.view = view;
        this.appInfoModel = AppInfoModel.getModelInstance();
    }

    @Override
    public void initData() {
        ThreadUtil.callInBackground(() -> {
            appInfoModel.initData(view.getContext().getPackageManager());
            ThreadUtil.callInUiThread(() -> view.onUpdateDataSet());
        });
    }

    @Override
    public void sortAppInfoList(int sortFlag) {
        ThreadUtil.callInBackground(() -> {
            appInfoModel.sortAppInfoList(sortFlag);
            ThreadUtil.callInUiThread(() -> view.onUpdateDataSet());
        });
    }

    @Override
    public List<ApiPercentBean> getApiPercentBeanList() {
        return appInfoModel.getApiPercentBeanList();
    }

    @Override
    public List<AppInfoBean> getAppInfoBeanList() {
        return appInfoModel.getAppInfoBeanList();
    }

}
