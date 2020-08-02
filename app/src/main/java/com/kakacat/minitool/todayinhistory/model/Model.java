package com.kakacat.minitool.todayinhistory.model;

import com.google.gson.Gson;
import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class Model {
    private static final String HOST = "http://api.juheapi.com/japi/toh?key=";
    private static final String KEY = "9aac7a73878303c4559180d1272e4a8e";

    private static Model model;

    private List<Article> articleList;
    private int year;
    private int month;
    private int day;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public void initData() {
        articleList = getArticleList();
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH) + 1;
        day = c.get(Calendar.DATE);
    }

    public void sendRequest(HttpCallback callback){
        HttpUtil.sendOkHttpRequest(getAddress(), callback);
    }

    public boolean handleHistoryResponse(Response response) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");
            Gson gson = new Gson();

            articleList.clear();
            for (int i = 0; i < result.length(); i++) {
                String str = result.getJSONObject(i).toString();
                Article article = gson.fromJson(str, Article.class);
                articleList.add(article);
            }
            return true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Article> getArticleList() {
        if (articleList == null) {
            articleList = new ArrayList<>();
        }
        return articleList;
    }

    private String getAddress() {
        return HOST + KEY + "&v=1.0&month=" + month + "&day=" + day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
