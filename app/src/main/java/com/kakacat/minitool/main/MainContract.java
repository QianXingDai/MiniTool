package com.kakacat.minitool.main;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.main.model.MainItem;

import java.util.List;

public interface MainContract {
    interface Presenter extends IPresenter{
        void initData();
        List<MainItem> getDailyList();
        List<MainItem> getGeekList();
    }

    interface View extends IView<Presenter>{
        void initData();
    }
}
