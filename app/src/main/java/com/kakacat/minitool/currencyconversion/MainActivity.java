package com.kakacat.minitool.currencyconversion;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.common.util.UiUtil;


public class MainActivity extends FrescoInitActivity implements Contract.View {

    private Contract.Presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CountryFragment countryFragment1;
    private CountryFragment countryFragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_conversion);

        initData();
        initView();
    }

    @Override
    public void initData() {
        getPresenter().initData();
    }

    @Override
    public void initView() {

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);

        super.initToolbar();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            presenter.refreshExchangeRate();
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        countryFragment1 = new CountryFragment(this, presenter.getCountryList(), 1);
        countryFragment2 = new CountryFragment(this, presenter.getCountryList(), 2);
        transaction.add(R.id.fragment_container, countryFragment1);
        transaction.add(R.id.fragment_container, countryFragment2);
        transaction.commit();
    }

    @Override
    public Contract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new Presenter(this);
        }
        return presenter;
    }

    @Override
    public void onTextChanged(CharSequence s, int flag) {
        if (flag == 1) {
            String result = presenter.getResult(s, countryFragment1.countryBean.getRate(), countryFragment2.countryBean.getRate());
            countryFragment2.et.setText(result);
        } else if (flag == 2) {
            String result = presenter.getResult(s, countryFragment2.countryBean.getRate(), countryFragment1.countryBean.getRate());
            countryFragment1.et.setText(result);
        }
    }

    @Override
    public void onRefreshExchangeRate(String result) {
        swipeRefreshLayout.setRefreshing(false);
        UiUtil.showSnackBar(swipeRefreshLayout, result);
    }

    @Override
    public Context getContext() {
        return this;
    }
}
