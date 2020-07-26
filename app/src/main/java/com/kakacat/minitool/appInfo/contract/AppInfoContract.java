package com.kakacat.minitool.appInfo.contract;

import android.content.pm.PackageInfo;

import com.kakacat.minitool.appInfo.model.ApiPercent;
import com.kakacat.minitool.appInfo.model.AppInfo;
import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

import java.util.List;

public interface AppInfoContract {

    interface Presenter extends IPresenter{
        void initData();
        void sortAppInfoList(int sortFlag);
        List<ApiPercent> getApiPercentList();
        List<PackageInfo> getPackageInfoList();
        List<AppInfo> getAppInfoList();
    }

    interface View extends IView<Presenter>{
        void onUpdateDataSet();
        void onUpdateAppInfoDataSet();
        void slideUpToTop();
        void showSortDialog();
    }
}
