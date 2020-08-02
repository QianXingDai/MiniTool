package com.kakacat.minitool.epidemicinquiry;

import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.common.util.ThreadUtil;
import com.kakacat.minitool.epidemicinquiry.model.GroupBean;
import com.kakacat.minitool.epidemicinquiry.model.Model;

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
        requestData();
    }

    @Override
    public void requestData() {
        HttpUtil.sendOkHttpRequest(Model.ADDRESS, new HttpCallback() {
            String result = "请求错误";

            @Override
            public void onSuccess(Response response) {
                if (model.handleResponse(response)) {
                    ThreadUtil.callInUiThread(() -> view.onUpdateViewSuccessful());
                } else {
                    result = "处理响应字符串失败";
                }
                ThreadUtil.callInUiThread(() -> view.onUpdateViewError(result));
            }

            @Override
            public void onError() {
                ThreadUtil.callInUiThread(() -> view.onUpdateViewError(result));
            }
        });
    }

    @Override
    public List<GroupBean> getGroupList() {
        return model.getGroupBeanList();
    }

}
