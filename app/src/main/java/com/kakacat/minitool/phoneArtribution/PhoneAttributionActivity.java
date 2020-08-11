package com.kakacat.minitool.phoneartribution;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.BaseActivity;
import com.kakacat.minitool.common.util.UiUtil;
import com.kakacat.minitool.phoneartribution.model.PhoneNumber;

public class PhoneAttributionActivity extends BaseActivity implements Contract.View {

    private Contract.Presenter presenter;

    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvAreaCode;
    private TextView tvZip;
    private TextView tvCompany;
    private TextView tvNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_attribution);

        initData();
        initView();
    }

    @Override
    public void initData() {
        presenter = new Presenter(this);
    }

    @Override
    public void initView() {
        UiUtil.setTranslucentStatusBarBlack(this);
        UiUtil.initToolbar(this,true);

        TextInputLayout til = findViewById(R.id.text_input_layout);
        EditText et = findViewById(R.id.et_input);
        tvProvince = findViewById(R.id.tv_province);
        tvCity = findViewById(R.id.tv_city);
        tvAreaCode = findViewById(R.id.tv_area_code);
        tvZip = findViewById(R.id.tv_zip);
        tvCompany = findViewById(R.id.tv_company);
        tvNumber = findViewById(R.id.tv_number);

        til.setStartIconOnClickListener(view -> presenter.requestData(et.getText().toString()));
    }

    @Override
    public void onRequestDataCallBack(PhoneNumber phoneNumber, String result) {
        if (phoneNumber != null) {
            tvProvince.setText(phoneNumber.getProvince());
            tvCity.setText(phoneNumber.getCity());
            tvAreaCode.setText(phoneNumber.getAreaCode());
            tvZip.setText(phoneNumber.getZip());
            tvCompany.setText(phoneNumber.getCompany());
            tvNumber.setText(phoneNumber.getNumber());
        }
        UiUtil.showToast(this, result);
    }
}
