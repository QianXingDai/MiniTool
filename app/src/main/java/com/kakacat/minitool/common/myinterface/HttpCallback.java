package com.kakacat.minitool.common.myinterface;

import okhttp3.Response;

public interface HttpCallback {
    void onSuccess(Response response);

    void onError();
}
