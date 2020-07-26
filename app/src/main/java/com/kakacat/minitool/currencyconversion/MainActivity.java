package com.kakacat.minitool.currencyconversion;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.ui.UiUtil;


public class MainActivity extends FrescoInitActivity implements Contract.View{

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
    public void initData(){
        setPresenter(new Presenter(this));
        presenter.initData();
    }

    @Override
    public void initView() {
        initToolbar();

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(()->{
            swipeRefreshLayout.setRefreshing(true);
            presenter.refreshExchangeRate();
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        countryFragment1 = new CountryFragment(this,presenter.getCountryList(),1);
        countryFragment2 = new CountryFragment(this,presenter.getCountryList(),2);
        transaction.add(R.id.view_pager,countryFragment1);
        transaction.add(R.id.view_pager,countryFragment2);
        transaction.commit();
    }

    @Override
    public void onTextChanged(CharSequence s,int flag){
        if(flag == 1){
            String result = presenter.getResult(s,countryFragment1.country.getRate(),countryFragment2.country.getRate());
            countryFragment2.et.setText(result);
        }else if(flag == 2){
            String result = presenter.getResult(s,countryFragment2.country.getRate(),countryFragment1.country.getRate());
            countryFragment1.et.setText(result);
        }
    }

    @Override
    public void onRefreshExchangeRate(int flag){
        String s = null;
        switch (flag){
            case Result.HANDLE_SUCCESS:{
                s = "刷新成功";
                break;
            }
            case Result.HANDLE_FAIL:{
                s = "处理响应字符串失败";
                break;
            }
            case Result.REQUEST_ERROR:{
                s = "请求失败";
                break;
            }
        }
        swipeRefreshLayout.setRefreshing(false);
        UiUtil.showHint(swipeRefreshLayout,s);
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

}
