package com.kakacat.minitool.inquireip;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.ui.UiUtil;

import bolts.Task;

public class InquireIpActivity extends AppCompatActivity implements Contract.View{

    private Contract.Presenter presenter;

    private TextView tvCountry;
    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvIsp;
    private TextView tvIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquire_ip);

        initData();
        initView();
    }


    @Override
    public void initData() {
        setPresenter(new Presenter(this));
    }

    @Override
    public void initView() {
        initToolbar();

        TextInputLayout til = findViewById(R.id.text_input_layout);
        EditText editText = findViewById(R.id.et_input_ip);
        tvCountry = findViewById(R.id.tv_country);
        tvProvince = findViewById(R.id.tv_province);
        tvCity = findViewById(R.id.tv_city);
        tvIsp = findViewById(R.id.tv_isp);
        tvIp = findViewById(R.id.tv_ip);

        til.setStartIconOnClickListener(view -> presenter.requestIpData(editText.getText().toString()));
    }

    @Override
    public void onUpdateDataCallBack(IpModel ipModel, int resultFlag){
        Task.call(() -> {
            switch (resultFlag){
                case Result.INPUT_ERROR:{
                    UiUtil.showToast(getContext(),"输入错误");
                    break;
                }
                case Result.HANDLE_FAIL:{
                    UiUtil.showToast(getContext(),"请求成功,但是处理响应数据错误");
                    break;
                }
                case Result.HANDLE_SUCCESS:{

                    tvCountry.setText(ipModel.getCountry());
                    tvProvince.setText(ipModel.getProvince());
                    tvCity.setText(ipModel.getCity());
                    tvIsp.setText(ipModel.getIsp());
                    tvIp.setText(ipModel.getIpAddress());
                    UiUtil.showToast(getContext(),"查询成功");
                }
                default:
                    break;
            }
            return null;
        },Task.UI_THREAD_EXECUTOR);
    }

    private void initToolbar(){
        setSupportActionBar(findViewById(R.id.toolbar_inquire_ip));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
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
