package com.kakacat.minitool.translation;

import android.content.Context;
import android.text.TextUtils;

import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.common.util.ThreadUtil;
import com.kakacat.minitool.translation.model.Model;

import java.util.List;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Model model;
    private Context context;

    public Presenter(Contract.View view) {
        this.view = view;
        this.model = new Model();
        this.context = view.getContext();
    }

    @Override
    public void initData() {
        model.initData();
    }

    @Override
    public List<String> getLanguageList1() {
        return model.getLanguageList1();
    }

    @Override
    public List<String> getLanguageList2() {
        return model.getLanguageList2();
    }

    @Override
    public List<String> getCollectionList() {
        return model.getCollectionList(context);
    }

    @Override
    public void addToMyFavourite(String source, String target) {
        if (TextUtils.isEmpty(source)) {
            view.onAddToMyFavouriteCallBack("请输入内容");
        } else {
            model.addToMyFavourite(source, target, context);
            view.onAddToMyFavouriteCallBack("收藏成功");
        }
    }

    @Override
    public void requestData(String input, CharSequence from, CharSequence to) {
        if (TextUtils.isEmpty(input)) {
            view.onRequestCallBack(null, "输入错误");
        } else {
            HttpUtil.Callback callback = new HttpUtil.Callback() {
                String result = "请求错误";
                @Override
                public void onSuccess(Response response) {
                    String s = model.handleTranslationResponse(response);
                    if (TextUtils.isEmpty(s)) {
                        result = "处理错误";
                    } else {
                        result = "请求成功";
                    }
                    ThreadUtil.callInUiThread(() -> view.onRequestCallBack(s, result));
                }

                @Override
                public void onError() {
                    ThreadUtil.callInUiThread(() -> view.onRequestCallBack(null, result));
                }
            };
            model.sendRequest(input,from,to,callback);
        }
    }
}
