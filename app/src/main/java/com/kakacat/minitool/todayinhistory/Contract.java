package com.kakacat.minitool.todayinhistory;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;

import java.util.List;

import okhttp3.Response;

public interface Contract{

    interface Presenter extends IPresenter{
        void initData();
        void refreshData();
        int getYear();
        int getMonth();
        void setMonth(int month);
        int getDay();
        void setDay(int day);
        boolean handleHistoryResponse(Response response, List<Article> articleList);
        List<Article> getArticleList();
    }

    interface View extends IView<Presenter>{
        void onUpdateDataCallBack(int resultFlag);
        void showCalendarDialog();
    }
}
