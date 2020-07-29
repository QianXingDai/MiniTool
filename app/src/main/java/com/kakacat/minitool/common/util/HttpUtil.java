package com.kakacat.minitool.common.util;

import com.kakacat.minitool.common.myinterface.HttpCallback;

import bolts.Task;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    private static OkHttpClient okHttpClient;

    public static void sendOkHttpRequest(String address, HttpCallback listener){
        Task.callInBackground(() -> {
            OkHttpClient client = getInstance();
            Request request = new Request.Builder()
                    .url(address)
                    .build();
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()){
                listener.onSuccess(response);
            }else{
                listener.onError();
            }
            return response;
        });
    }


    private static OkHttpClient getInstance(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
}
