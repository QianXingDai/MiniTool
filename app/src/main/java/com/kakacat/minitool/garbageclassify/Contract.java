package com.kakacat.minitool.garbageclassify;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.garbageclassify.model.Garbage;

import java.util.List;

import okhttp3.Response;

public interface Contract {

    interface Presenter extends IPresenter{
        void requestData(String s);
        boolean handleResponse(Response response);
        List<Garbage> getGarbageList();
    }

    interface View extends IView<Presenter>{
        void requestData(String s);
        void onRequestCallBack(int resultFlag);
        void showContentView(int position);
    }
}
