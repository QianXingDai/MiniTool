package com.kakacat.minitool.garbageclassify;

import android.text.TextUtils;

import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.common.util.ThreadUtil;
import com.kakacat.minitool.garbageclassify.model.Garbage;
import com.kakacat.minitool.garbageclassify.model.Model;

import java.util.List;

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
    public void requestData(String s) {
        if (TextUtils.isEmpty(s)) {
            view.onRequestCallBack("请输入内容");
        } else {
            String address = model.getAddress(s);
            HttpUtil.sendOkHttpRequest(address, new HttpCallback() {
                String result = "输入错误或者找不到结果";

                @Override
                public void onSuccess(Response response) {
                    if (model.handleResponse(response)) {
                        result = "处理成功";
                    } else {
                        result = "处理响应字符串失败";
                    }
                    ThreadUtil.callInUiThread(() -> view.onRequestCallBack(result));
                }

                @Override
                public void onError() {
                    ThreadUtil.callInUiThread(() -> view.onRequestCallBack(result));
                }
            });
        }
    }

    @Override
    public List<Garbage> getGarbageList() {
        return model.getGarbageList();
    }
}
