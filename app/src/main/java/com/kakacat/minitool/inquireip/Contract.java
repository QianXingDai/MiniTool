package com.kakacat.minitool.inquireip;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

public interface Contract {

    interface Presenter extends IPresenter {
        void requestIpData(String input);
    }

    interface View extends IView<Presenter> {
        void onUpdateDataCallBack(IpBean ipBean, String result);
    }
}
