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

    private static Model model;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public PhoneNumber response2PhoneNumber(@NotNull Response response) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            if (!TextUtils.isEmpty(s)) {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject result = jsonObject.getJSONObject("result");
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setProvince(result.getString("province"));
                phoneNumber.setCity(result.getString("city"));
                phoneNumber.setAreaCode(result.getString("areacode"));
                phoneNumber.setZip(result.getString("zip"));
                phoneNumber.setCompany(result.getString("company"));
                return phoneNumber;
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
