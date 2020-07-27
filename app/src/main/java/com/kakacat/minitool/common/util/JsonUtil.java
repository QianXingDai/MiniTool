package com.kakacat.minitool.common.util;

import android.text.TextUtils;

import com.kakacat.minitool.epidemicsituation.EpidemicData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class JsonUtil {

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
