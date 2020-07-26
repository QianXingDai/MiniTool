package com.kakacat.minitool.todayinhistory;

import com.kakacat.minitool.common.constant.AppKey;
import com.kakacat.minitool.common.constant.Host;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private List<Article> articleList;

    private int year;
    private int month;
    private int day;

    public Presenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void initData(){
        articleList = getArticleList();
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);
    }

    @Override
    public void refreshData(){
        HttpUtil.sendOkHttpRequest(getAddress(), new HttpCallback() {
            int resultFlag = Result.REQUEST_ERROR;
            @Override
            public void onSuccess(Response response) {
                if(!Article.handleHistoryResponse(response,getArticleList())){
                    resultFlag = Result.HANDLE_FAIL;
                }else{
                    resultFlag = Result.HANDLE_SUCCESS;
                }
                view.onUpdateDataCallBack(resultFlag);
            }

            @Override
            public void onError() {
                view.onUpdateDataCallBack(resultFlag);
            }
        });
    }

    @Override
    public List<Article> getArticleList() {
        if(articleList == null){
            articleList = new ArrayList<>();
        }
        return articleList;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    private String getAddress(){
        return Host.TODAY_IN_HISTORY_HOST + AppKey.TODAY_IN_HISTORY_KEY + "&v=1.0&month=" + month + "&day=" + day;
    }
}
