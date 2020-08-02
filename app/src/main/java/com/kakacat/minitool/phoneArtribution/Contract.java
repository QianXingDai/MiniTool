package com.kakacat.minitool.phoneartribution;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.phoneartribution.model.PhoneNumber;

public interface Contract {

    interface Presenter extends IPresenter {
        void requestData(String phoneNumber);
    }

    interface View extends IView<Presenter> {
        void onRequestDataCallBack(PhoneNumber phoneNumber, String result);
    }
}
