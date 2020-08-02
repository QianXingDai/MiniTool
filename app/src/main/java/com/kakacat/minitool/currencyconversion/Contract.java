package com.kakacat.minitool.currencyconversion;

import com.kakacat.minitool.common.base.IPresenter;
import com.kakacat.minitool.common.base.IView;
import com.kakacat.minitool.currencyconversion.model.CountryBean;

import java.util.List;

public interface Contract {

    interface Presenter extends IPresenter {
        void initData();

        void refreshExchangeRate();

        String getResult(CharSequence val, double rate1, double rate2);

        List<CountryBean> getCountryList();
    }

    interface View extends IView<Presenter> {
        void initData();

        void onRefreshExchangeRate(String result);

        void onTextChanged(CharSequence s, int flag);
    }
}
