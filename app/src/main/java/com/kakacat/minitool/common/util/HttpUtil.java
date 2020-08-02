package com.kakacat.minitool.common.util;

import com.kakacat.minitool.common.myinterface.HttpCallback;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    private static OkHttpClient okHttpClient;

    public static void sendOkHttpRequest(String address, HttpCallback callback) {
        sendOkHttpRequest(address, callback, null);
    }

    public static void sendOkHttpRequest(String address, HttpCallback callback, RequestBody requestBody) {
        ThreadUtil.callInBackground(() -> {
            OkHttpClient client = getInstance();
            Request.Builder builder = new Request.Builder().url(address);
            Response response = null;
            Request request;

            if (requestBody != null) {
                request = builder.post(requestBody).build();
            } else {
                request = builder.get().build();
            }

            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert response != null;
            if (response.isSuccessful()) {
                callback.onSuccess(response);
            } else {
                callback.onError();
            }
        });
    }


    private static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }
}
