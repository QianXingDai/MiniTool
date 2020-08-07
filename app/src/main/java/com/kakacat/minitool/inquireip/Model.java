package com.kakacat.minitool.inquireip;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Response;

public class Model {

    private static final String QUERY_IP_HOST = "http://apis.juhe.cn/ip/ipNew?ip=";
    private static final String QUERY_IP_KEY = "ad7f15652b5f7e7799fefa92a5246d4a";

    private static Model model;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public String getAddress(String input) {
        return QUERY_IP_HOST + input + "&key=" + QUERY_IP_KEY;
    }

    public IpBean handleIpDataResponse(@NotNull Response response) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            if (TextUtils.isEmpty(s)) {
                return null;
            }
            IpBean ipBean = new IpBean();
            JSONObject jsonObject = new JSONObject(s);
            if (!jsonObject.getString("resultcode").equals("200")) {
                return null;
            }
            JSONObject result = jsonObject.getJSONObject("result");
            ipBean.setCountry(result.getString("Country"));
            ipBean.setProvince(result.getString("Province"));
            ipBean.setCity(result.getString("City"));
            ipBean.setIsp(result.getString("Isp"));

            return ipBean;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
