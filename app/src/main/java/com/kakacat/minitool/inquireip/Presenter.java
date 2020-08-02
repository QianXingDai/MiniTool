package com.kakacat.minitool.inquireip;

import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.common.util.ThreadUtil;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;

    public Presenter(Contract.View view) {
        this.view = view;
        this.model = Model.getInstance();
    }

    @Override
    public void initData() {

    }

    @Override
    public void requestIpData(String input) {
        if (!IpBean.checkIp(input)) {
            view.onUpdateDataCallBack(null, "输入格式错误");
        } else {
            String address = model.getAddress(input);
            HttpUtil.sendOkHttpRequest(address, new HttpCallback() {
                String result = "请求错误";

                @Override
                public void onSuccess(Response response) {
                    IpBean ipBean = model.handleIpDataResponse(response);
                    if (ipBean == null) {
                        result = "处理响应失败";
                    } else {
                        result = "处理成功";
                    }
                    ThreadUtil.callInUiThread(() -> view.onUpdateDataCallBack(ipBean, result));
                }

                @Override
                public void onError() {
                    ThreadUtil.callInUiThread(() -> view.onUpdateDataCallBack(null, result));
                }
            });
        }
    }
}
