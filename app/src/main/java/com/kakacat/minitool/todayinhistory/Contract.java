package com.kakacat.minitool.todayinhistory;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.todayinhistory.model.Article;

import java.util.List;

public interface Contract {

    interface Presenter extends IPresenter {

        void refreshData();

        int getYear();

        int getMonth();

        void setMonth(int month);

        int getDay();

        void setDay(int day);

        List<Article> getArticleList();
    }

    interface View extends IView<Presenter> {
        void onUpdateDataCallBack(String result, boolean needRefresh);

        void showCalendarDialog();
    }
}
