package com.kakacat.minitool.inquireip;

import com.kakacat.minitool.common.constant.AppKey;
import com.kakacat.minitool.common.constant.Host;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;

    public Presenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void requestIpData(String input){
        if(!IpModel.checkIp(input)){
            view.onUpdateDataCallBack(null, Result.INPUT_ERROR);
        }else{
            String address = Host.QUERY_IP_HOST + input + "&key=" + AppKey.QUERY_IP_KEY;
            HttpUtil.sendOkHttpRequest(address, new HttpCallback() {
                IpModel ipModel = null;
                int resultFlag = Result.REQUEST_ERROR;

                @Override
                public void onSuccess(Response response) {
                    ipModel = IpModel.handleIpDataResponse(response);
                    if(ipModel == null){
                        resultFlag = Result.HANDLE_FAIL;
                    }else{
                        ipModel.setIpAddress(input);
                        resultFlag = Result.HANDLE_SUCCESS;
                        view.onUpdateDataCallBack(ipModel,Result.HANDLE_SUCCESS);
                    }
                }

                @Override
                public void onError() {
                    view.onUpdateDataCallBack(ipModel,resultFlag);
                }
            });
        }
    }


    @Override
    public void initData() {

    }
}
