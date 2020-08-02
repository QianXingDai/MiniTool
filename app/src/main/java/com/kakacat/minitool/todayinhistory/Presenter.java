package com.kakacat.minitool.todayinhistory;

import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.ThreadUtil;
import com.kakacat.minitool.todayinhistory.model.Article;
import com.kakacat.minitool.todayinhistory.model.Model;

import java.util.List;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;

    public Presenter(Contract.View view) {
        this.view = view;
        this.model = Model.getInstance();
    }

    @Override
    public void initData() {
        model.initData();
    }

    @Override
    public void refreshData() {
        model.sendRequest(new HttpCallback() {
            String result = "请求错误";
            boolean needRefresh = false;

            @Override
            public void onSuccess(Response response) {
                if (!model.handleHistoryResponse(response)) {
                    result = "处理失败";
                } else {
                    result = "处理成功";
                    needRefresh = true;
                }
                ThreadUtil.callInUiThread(() -> view.onUpdateDataCallBack(result, needRefresh));
            }

            @Override
            public void onError() {
                ThreadUtil.callInUiThread(() -> view.onUpdateDataCallBack(result, needRefresh));
            }
        });
    }


    @Override
    public List<Article> getArticleList() {
        return model.getArticleList();
    }

    public int getYear() {
        return model.getYear();
    }

    public int getMonth() {
        return model.getMonth();
    }

    public void setMonth(int month) {
        model.setMonth(month);
    }

    public int getDay() {
        return model.getDay();
    }

    public void setDay(int day) {
        model.setDay(day);
    }
}
