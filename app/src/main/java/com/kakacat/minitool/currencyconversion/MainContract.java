package com.kakacat.minitool.currencyconversion;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.currencyconversion.model.Country;

import java.util.List;

public interface MainContract {

    interface Presenter extends IPresenter{
        void initData();
        void refreshExchangeRate();
        boolean handleRateResponse(String response);
        String getResult(CharSequence val,double rate1,double rate2);
        List<Country> getCountryList();
    }

    interface View extends IView<Presenter>{
        void initData();
        void onRefreshExchangeRate(int flag);
        void onTextChanged(CharSequence s,int flag);
    }
}
