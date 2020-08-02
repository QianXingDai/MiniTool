package com.kakacat.minitool.appInfo.contract;

import com.kakacat.minitool.appInfo.activity.AppDetailActivity;
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean;
import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

public interface AppDetailContract {

    interface Presenter extends IPresenter {
        void initData();

        void saveIcon();

        void openMarket();

        void saveApk();

        void openDetailInSetting();

        void copyMd5();

        AppInfoBean getAppInfoBean();
    }

    interface View extends IView<Presenter> {
        void saveIcon();

        void saveApk();

        void onSaveIconResult(String result);

        void onSaveApkResult(String result);

        void onCopyMd5Result(String result);

        AppDetailActivity getActivity();
    }
}
