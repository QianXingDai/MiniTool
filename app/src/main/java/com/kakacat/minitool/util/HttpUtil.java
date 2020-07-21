package com.kakacat.minitool.util;

import com.kakacat.minitool.common.HttpCallbackListener;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {

    private static OkHttpClient okHttpClient;

    public static void sendOkHttpRequest(String address, HttpCallbackListener listener){
        new Thread(()->{
            try{
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
            }catch (IOException e){
                e.printStackTrace();
            }
        }).start();
    }


    public static OkHttpClient getInstance(){
        if(okHttpClient == null)
            okHttpClient = new OkHttpClient();
        return okHttpClient;
    }
}
