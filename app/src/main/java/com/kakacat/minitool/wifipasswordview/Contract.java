package com.kakacat.minitool.wifipasswordview;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.wifipasswordview.model.Wifi;

import java.util.List;

public interface Contract {

    interface Presenter extends IPresenter {
        void copyToClipboard(int position);

        List<Wifi> getWifiList();
    }

    interface View extends IView<Presenter> {
        void onCopyCallback(String result);

        void onGetWifiDataCallBack(String result);
    }
}
