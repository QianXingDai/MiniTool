package com.kakacat.minitool.expressinquiry;

import android.content.Context;
import android.text.TextUtils;

import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.SystemUtil;
import com.kakacat.minitool.common.util.ThreadUtil;
import com.kakacat.minitool.expressinquiry.model.Delivery;
import com.kakacat.minitool.expressinquiry.model.Model;

import java.util.List;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;
    private Context context;

    public Presenter(Contract.View view) {
        this.view = view;
        this.model = Model.getInstance();
        this.context = view.getContext();
    }

    @Override
    public void initData() {
        model.initData(context);
    }

    @Override
    public void saveData() {
        model.saveDataToLocal(context);
    }

    @Override
    public void requestData(String code) {
        if (TextUtils.isEmpty(code)) {
            view.onRequestCallback("输入为空", false);
        } else if (!model.checkInput(code)) {
            view.onRequestCallback("输入错误,请检查输入", false);
        } else {
            if (model.isInSignedList(code)) {
                view.onRequestCallback("已经在签收列表哟", false);
            } else {
                model.sendRequest(code, new HttpCallback() {
                    @Override
                    public void onSuccess(Response response) {
                        if (model.handleResponse(response, code)) {
                            ThreadUtil.callInUiThread(() -> view.onRequestCallback("查询成功", true));
                        } else {
                            ThreadUtil.callInUiThread(() -> view.onRequestCallback("处理错误", false));
                        }
                    }

                    @Override
                    public void onError() {
                        ThreadUtil.callInUiThread(() -> view.onRequestCallback("请求错误", false));
                    }
                });
            }
        }
    }

    @Override
    public void refreshAll() {
        List<Delivery> list = getDeliveryList(0);
        if (list.size() == 0) {
            ThreadUtil.callInUiThread(() -> view.onRequestCallback("全部签收了哟", false));
        } else {
            list.forEach(delivery -> requestData(delivery.getCode()));
        }
    }

    @Override
    public CharSequence getDataFromClipBoard() {
        return SystemUtil.getDataFormClipBoard(context);
    }

    @Override
    public List<Delivery> getDeliveryList(int index) {
        return model.getList(index);
    }
}
