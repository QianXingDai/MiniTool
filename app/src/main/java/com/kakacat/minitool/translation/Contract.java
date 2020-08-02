package com.kakacat.minitool.translation;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

import java.util.List;

public interface Contract {

    interface View extends IView<Presenter> {
        void onAddToMyFavouriteCallBack(String s);

        void showCollectionWindow();

        void onRequestCallBack(String result, String warn);

        void showSelectLanguageWindow(int flag);
    }

    interface Presenter extends IPresenter {
        List<String> getCollectionList();

        List<String> getLanguageList1();

        List<String> getLanguageList2();

        void requestData(String input, CharSequence from, CharSequence to);

        void addToMyFavourite(String source, String target);
    }
}
