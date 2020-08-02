package com.kakacat.minitool.garbageclassify;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.garbageclassify.model.Garbage;

import java.util.List;

public interface Contract {

    interface Presenter extends IPresenter {
        void requestData(String s);

        List<Garbage> getGarbageList();
    }

    interface View extends IView<Presenter> {
        void requestData(String s);

        void onRequestCallBack(String result);

        void showContentView(int position);
    }
}
