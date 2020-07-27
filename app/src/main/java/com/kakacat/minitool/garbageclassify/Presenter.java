package com.kakacat.minitool.garbageclassify;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.kakacat.minitool.common.constant.AppKey;
import com.kakacat.minitool.common.constant.Host;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;
import com.kakacat.minitool.garbageclassify.model.Garbage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private List<Garbage> garbageList;

    public Presenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void initData() {
        garbageList = new ArrayList<>();
    }

    @Override
    public void requestData(String s) {
        if(TextUtils.isEmpty(s)){
            view.onRequestCallBack(Result.INPUT_ERROR);
        }else{
            String address = Host.GARBAGE_QUERY_HOST + AppKey.GARBAGE_QUERY + "&word=" + s;
            HttpUtil.sendOkHttpRequest(address, new HttpCallback() {
                int resultFlag = Result.REQUEST_ERROR;
                @Override
                public void onSuccess(Response response) {
                    if(handleResponse(response)){
                        resultFlag = Result.HANDLE_SUCCESS;
                    }else{
                        resultFlag = Result.HANDLE_FAIL;
                    }
                    view.onRequestCallBack(resultFlag);
                }
                @Override
                public void onError() {
                    view.onRequestCallBack(Result.REQUEST_ERROR);
                }
            });
        }
    }

    @Override
    public boolean handleResponse(Response response){
        try {
            String s = Objects.requireNonNull(response.body()).string();
            if(TextUtils.isEmpty(s)){
                return false;
            }
            JSONObject jsonObject = new JSONObject(s);
            JSONArray garbageObjects = jsonObject.getJSONArray("newslist");
            Gson gson = new Gson();

            garbageList.clear();
            for(int i = 0; i < garbageObjects.length(); i++){
                String str = garbageObjects.getJSONObject(i).toString();
                garbageList.add(gson.fromJson(str,Garbage.class));
            }
            return true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Garbage> getGarbageList(){
        return garbageList;
    }
}
