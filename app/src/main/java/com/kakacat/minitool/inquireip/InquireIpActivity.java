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
import com.kakacat.minitool.common.util.UiUtil;

public class InquireIpActivity extends AppCompatActivity implements Contract.View {

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
        getPresenter();
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
    public Contract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new Presenter(this);
        }
        return presenter;
    }

    @Override
    public void onUpdateDataCallBack(IpBean ipBean, String result) {
        UiUtil.showToast(this, result);
        if (ipBean != null) {
            tvCountry.setText(ipBean.getCountry());
            tvProvince.setText(ipBean.getProvince());
            tvCity.setText(ipBean.getCity());
            tvIsp.setText(ipBean.getIsp());
            tvIp.setText(ipBean.getIpAddress());
        }
    }

    private void initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar_inquire_ip));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }


}
