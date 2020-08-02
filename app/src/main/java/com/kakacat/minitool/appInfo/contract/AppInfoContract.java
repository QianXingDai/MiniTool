package com.kakacat.minitool.appInfo.contract;

import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean;
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean;
import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

import java.util.List;

public interface AppInfoContract {

    interface Presenter extends IPresenter {
        void initData();

        void sortAppInfoList(int sortFlag);

        List<ApiPercentBean> getApiPercentBeanList();

        List<AppInfoBean> getAppInfoBeanList();
    }

    interface View extends IView<Presenter> {
        void onUpdateDataSet();

        void slideUpToTop();

        void showSortDialog();
    }
}
