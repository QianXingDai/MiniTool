package com.kakacat.minitool.phoneartribution.model;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Response;

public class Model {

    private static final String HOST = "http://apis.juhe.cn/mobile/get?phone=";
    private static final String KEY = "a61898e25da1484f93ccf01e2ebe6ff7";

    public PhoneNumber response2PhoneNumber(@NotNull Response response) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            if (!TextUtils.isEmpty(s)) {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject result = jsonObject.getJSONObject("result");
                return new PhoneNumber(null,result.getString("province"),result.getString("city"),result.getString("areacode"),result.getString("zip"),result.getString("company"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getAddress(String number) {
        return HOST + number + "&key=" + KEY;
    }
}
