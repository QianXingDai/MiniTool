package com.kakacat.minitool.expressinquiry;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.expressinquiry.model.Delivery;

import java.util.List;

public interface Contract {

    interface View extends IView<Presenter> {
        void onRequestCallback(String result, boolean needRefresh);

        void showWindows(android.view.View fab);
    }

    interface Presenter extends IPresenter {
        void saveData();

        void requestData(String code);

        void refreshAll();

        CharSequence getDataFromClipBoard();

        List<Delivery> getDeliveryList(int index);
    }
}

