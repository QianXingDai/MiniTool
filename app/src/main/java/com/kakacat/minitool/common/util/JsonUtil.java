package com.kakacat.minitool.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.kakacat.minitool.currencyconversion.model.Rate;
import com.kakacat.minitool.garbageClassification.Garbage;
import com.kakacat.minitool.globalOutbreak.EpidemicData;
import com.kakacat.minitool.inquireIp.Data;
import com.kakacat.minitool.todayInHistory.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonUtil {

    public static Data handleIpDataResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                Data data = new Data();
                JSONObject jsonObject = new JSONObject(response);
                String resultCode = jsonObject.getString("resultcode");
                if(!resultCode.equals("200")) Log.d("hhh","查询ip数据失败");
                   else{
                    JSONObject result = jsonObject.getJSONObject("result");
                    data.setCountry(result.getString("Country"));
                    data.setProvince(result.getString("Province"));
                    data.setCity(result.getString("City"));
                    data.setIsp(result.getString("Isp"));
                    return data;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }else{
            Log.d("hhh","数据为空");
        }
        return null;
    }


    public static void writeRateToLocal(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("rate",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("us",(float) Rate.us);
        editor.putFloat("eu",(float) Rate.eu);
        editor.putFloat("hk",(float) Rate.hk);
        editor.putFloat("jp",(float) Rate.jp);
        editor.putFloat("uk",(float) Rate.uk);
        editor.putFloat("au",(float) Rate.au);
        editor.putFloat("ca",(float) Rate.ca);
        editor.putFloat("th",(float) Rate.th);
        editor.putFloat("sg",(float) Rate.sg);
        editor.putFloat("ch",(float) Rate.ch);
        editor.putFloat("dk",(float) Rate.dk);
        editor.putFloat("ma",(float) Rate.ma);
        editor.putFloat("my",(float) Rate.my);
        editor.putFloat("no",(float) Rate.no);
        editor.putFloat("nz",(float) Rate.nz);
        editor.putFloat("ph",(float) Rate.ph);
        editor.putFloat("ru",(float) Rate.ru);
        editor.putFloat("se",(float) Rate.se);
        editor.putFloat("tw",(float) Rate.tw);
        editor.putFloat("br",(float) Rate.br);
        editor.putFloat("kr",(float) Rate.kr);
        editor.putFloat("za",(float) Rate.za);
        editor.apply();
    }

    public static void readRateFromLocal(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("rate",Context.MODE_PRIVATE);
        Rate.us = sharedPreferences.getFloat("us", (float) Rate.us);
        Rate.eu = sharedPreferences.getFloat("eu", (float) Rate.eu);
        Rate.hk = sharedPreferences.getFloat("hk", (float) Rate.hk);
        Rate.jp = sharedPreferences.getFloat("jp", (float) Rate.jp);
        Rate.uk = sharedPreferences.getFloat("uk", (float) Rate.uk);
        Rate.au = sharedPreferences.getFloat("au", (float) Rate.au);
        Rate.ca = sharedPreferences.getFloat("ca", (float) Rate.ca);
        Rate.th = sharedPreferences.getFloat("th", (float) Rate.th);
        Rate.sg = sharedPreferences.getFloat("sg", (float) Rate.sg);
        Rate.ch = sharedPreferences.getFloat("ch", (float) Rate.ch);
        Rate.dk = sharedPreferences.getFloat("dk", (float) Rate.dk);
        Rate.ma = sharedPreferences.getFloat("ma", (float) Rate.ma);
        Rate.my = sharedPreferences.getFloat("my", (float) Rate.my);
        Rate.no = sharedPreferences.getFloat("no", (float) Rate.no);
        Rate.nz = sharedPreferences.getFloat("nz", (float) Rate.nz);
        Rate.ph = sharedPreferences.getFloat("ph", (float) Rate.ph);
        Rate.ru = sharedPreferences.getFloat("ru", (float) Rate.ru);
        Rate.se = sharedPreferences.getFloat("se", (float) Rate.se);
        Rate.tw = sharedPreferences.getFloat("tw", (float) Rate.tw);
        Rate.br = sharedPreferences.getFloat("br", (float) Rate.br);
        Rate.kr = sharedPreferences.getFloat("kr", (float) Rate.kr);
        Rate.za = sharedPreferences.getFloat("za", (float) Rate.za);
    }

    public static void handleHistoryResponse(String s, List<Article> articleList){
        if(!TextUtils.isEmpty(s)){
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray result = jsonObject.getJSONArray("result");
                Gson gson = new Gson();

                for(int i = 0; i < result.length(); i++){
                    String str = result.getJSONObject(i).toString();
                    Article article = gson.fromJson(str,Article.class);
                    articleList.add(article);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
    }

    public static void handleGarbageResponse(String s, List<Garbage> garbageList){
        if(!TextUtils.isEmpty(s)){
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray garbageObjects = jsonObject.getJSONArray("newslist");
                Gson gson = new Gson();

                for(int i = 0; i < garbageObjects.length(); i++){
                    String str = garbageObjects.getJSONObject(i).toString();
                    garbageList.add(gson.fromJson(str,Garbage.class));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void handleEpidemicResponse(String s, List<List<EpidemicData>> list){
        if(!TextUtils.isEmpty(s)){
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("newslist");

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    EpidemicData data = new EpidemicData();
                    data.setModifyTime(System.currentTimeMillis());
                    data.setContinents(jsonObject1.getString("continents"));
                    data.setProvinceName(jsonObject1.getString("provinceName"));
                    data.setCurrentConfirmedCount(jsonObject1.getInt("currentConfirmedCount"));
                    data.setConfirmedCount(jsonObject1.getInt("confirmedCount"));
                    data.setSuspectedCount(jsonObject1.getInt("curedCount"));
                    data.setDeadCount(jsonObject1.getInt("deadCount"));
                    data.setDeadCountRate(jsonObject1.getDouble("deadRate"));
                    data.setCountryShortCode(jsonObject1.getString("countryShortCode"));
                    data.setDeadCountRank(jsonObject1.getInt("deadCountRank"));

                    String continent = jsonObject1.getString("continents");
                    switch (continent){
                        case "亚洲":{
                            list.get(0).add(data);
                            break;
                        }
                        case "欧洲":{
                            list.get(1).add(data);
                            break;
                        }
                        case "北美洲":{
                            list.get(2).add(data);
                            break;
                        }
                        case "南美洲":{
                            list.get(3).add(data);
                            break;
                        }
                        case "非洲":{
                            list.get(4).add(data);
                            break;
                        }
                        default:{
                            list.get(5).add(data);
                            break;
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }

    public static String handleTranslationResponse(String s){
        if(!TextUtils.isEmpty(s)){
            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObject1 = jsonObject.getJSONArray("trans_result").getJSONObject(0);
                return jsonObject1.getString("dst");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}