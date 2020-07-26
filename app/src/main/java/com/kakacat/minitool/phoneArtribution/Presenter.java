package com.kakacat.minitool.phoneartribution;

import android.text.TextUtils;

import com.kakacat.minitool.common.constant.AppKey;
import com.kakacat.minitool.common.constant.Host;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;

import okhttp3.Response;

import static com.kakacat.minitool.common.constant.Result.INPUT_ERROR;
import static com.kakacat.minitool.common.constant.Result.REQUEST_ERROR;

public class Presenter implements Contract.Presenter {




    private Contract.View view;

    public Presenter(Contract.View view) {
        this.view = view;
    }


    @Override
    public void requestData(String number) {
        if(TextUtils.isEmpty(number) || number.length() != 11){
            view.onRequestDataCallBack(null,INPUT_ERROR);
        }else{
            String address = Host.PHONE_NUMBER_HOST + number + "&key=" + AppKey.PHONE_NUMBER_KEY;
            HttpUtil.sendOkHttpRequest(address, new HttpCallback() {
                int resultFlag = Result.REQUEST_ERROR;
                @Override
                public void onSuccess(Response response) {
                    PhoneNumber phoneNumber = PhoneNumber.response2PhoneNumber(response);
                    if(phoneNumber == null){
                        resultFlag = Result.HANDLE_FAIL;
                    }else{
                        resultFlag = Result.HANDLE_SUCCESS;
                        phoneNumber.setNumber(number);
                        view.onRequestDataCallBack(phoneNumber, Result.HANDLE_SUCCESS);
                    }
                }

                @Override
                public void onError() {
                    view.onRequestDataCallBack(null,REQUEST_ERROR);
                }
            });
        }
    }
}
