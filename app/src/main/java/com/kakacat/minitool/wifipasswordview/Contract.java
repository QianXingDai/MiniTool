package com.kakacat.minitool.wifipasswordview;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

import java.util.List;

public interface Contract {

    interface Presenter extends IPresenter{
        List<Wifi> getWifiList();
    }

    interface View extends IView<Presenter>{
        void onGetWifiDataCallBack(String result);
    }
}
