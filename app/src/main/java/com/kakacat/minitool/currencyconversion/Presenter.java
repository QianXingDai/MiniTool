package com.kakacat.minitool.currencyconversion;

import android.content.Context;

import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.common.util.ThreadUtil;
import com.kakacat.minitool.currencyconversion.model.CountryBean;
import com.kakacat.minitool.currencyconversion.model.Model;

import java.util.List;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;
    private Context context;

    public Presenter(Contract.View view) {
        this.view = view;
        this.context = view.getContext();
        this.model = Model.getInstance();
    }

    @Override
    public void initData() {
        model.readRateFromLocal(context);
        model.getCountryBeanList();
    }

    @Override
    public void refreshExchangeRate() {
        HttpUtil.sendOkHttpRequest(Model.REQUEST_ADDRESS, new HttpCallback() {
            String result = "请求错误";

            @Override
            public void onSuccess(Response response) {
                if (!model.handleResponse(response)) {
                    result = "处理相应字符串错误";
                } else {
                    result = "处理成功";
                }
                ThreadUtil.callInUiThread(() -> view.onRefreshExchangeRate(result));
            }

            @Override
            public void onError() {
                ThreadUtil.callInUiThread(() -> view.onRefreshExchangeRate(result));
            }
        });
    }

    @Override
    public String getResult(CharSequence val, double rate1, double rate2) {
        return model.getResult(val, rate1, rate2);
    }

    @Override
    public List<CountryBean> getCountryList() {
        return model.getCountryBeanList();
    }
}
