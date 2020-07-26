package com.kakacat.minitool.todayinhistory;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class Article {

    private String _id;
    private String title;
    private String pic;
    private String year;
    private String month;
    private String day;
    private String des;
    private String lunar;

    public Article(){}

    public Article(String _id, String title, String pic, String year, String month, String day, String des, String lunar) {
        this._id = _id;
        this.title = title;
        this.pic = pic;
        this.year = year;
        this.month = month;
        this.day = day;
        this.des = des;
        this.lunar = lunar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLunar() {
        return lunar;
    }

    public void setLunar(String lunar) {
        this.lunar = lunar;
    }

    public static boolean handleHistoryResponse(Response response, List<Article> articleList){
        try{
            String s = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray result = jsonObject.getJSONArray("result");
            Gson gson = new Gson();

            articleList.clear();
            for(int i = 0; i < result.length(); i++){
                String str = result.getJSONObject(i).toString();
                Article article = gson.fromJson(str,Article.class);
                articleList.add(article);
            }
            return true;
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return false;
    }
}
