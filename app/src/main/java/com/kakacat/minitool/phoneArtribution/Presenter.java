package com.kakacat.minitool.phoneartribution;

import android.text.TextUtils;
import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.common.util.ThreadUtil;
import com.kakacat.minitool.phoneartribution.model.Model;
import com.kakacat.minitool.phoneartribution.model.PhoneNumber;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;

    public Presenter(Contract.View view) {
        this.view = view;
        this.model = new Model();
    }

    @Override
    public void requestData(String number) {
        if (TextUtils.isEmpty(number) || number.length() != 11) {
            view.onRequestDataCallBack(null, "输入错误");
        } else {
            String address = model.getAddress(number);
            HttpUtil.sendOkHttpRequest(address, new HttpUtil.Callback() {
                String result = "请求错误";

                @Override
                public void onSuccess(Response response) {
                    PhoneNumber phoneNumber = model.response2PhoneNumber(response);
                    if (phoneNumber == null) {
                        result = "处理响应数据失败";
                    } else {
                        result = "请求成功";
                        phoneNumber.setNumber(number);
                    }
                    ThreadUtil.callInUiThread(() -> view.onRequestDataCallBack(phoneNumber, result));
                }

                @Override
                public void onError() {
                    ThreadUtil.callInUiThread(() -> view.onRequestDataCallBack(null, result));
                }
            });
        }
    }

    @Override
    public void initData() {

    }
}
