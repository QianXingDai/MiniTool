package com.kakacat.minitool.garbageclassify.model;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class Model {
    private static final String HOST = "https://api.tianapi.com/txapi/lajifenlei/index?key=";
    private static final String KEY = "c3886d3637d56c2730a4a7066fb9fa47";

    private static Model model;
    private List<Garbage> garbageList;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public boolean handleResponse(Response response) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            if (TextUtils.isEmpty(s)) {
                return false;
            }
            JSONObject jsonObject = new JSONObject(s);
            JSONArray garbageObjects = jsonObject.getJSONArray("newslist");
            Gson gson = new Gson();

            getGarbageList().clear();
            for (int i = 0; i < garbageObjects.length(); i++) {
                String str = garbageObjects.getJSONObject(i).toString();
                garbageList.add(gson.fromJson(str, Garbage.class));
            }
            return true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getAddress(String input) {
        return HOST + KEY + "&word=" + input;
    }

    public List<Garbage> getGarbageList() {
        if (garbageList == null) {
            garbageList = new ArrayList<>();
        }
        return garbageList;
    }
}
