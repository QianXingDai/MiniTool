package com.kakacat.minitool.translation.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.kakacat.minitool.common.util.EncryptionUtil;
import com.kakacat.minitool.common.util.HttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Response;

public class Model {

    //百度提供的翻译查询接口appid
    private static final String TRANSLATE_APP_ID = "20200420000425201";
    private static final String TRANSLATE_SECRET_KEY = "qceN7y1RBpEp8x1g47_i";
    //百度提供的翻译查询接口
    public static final String TRANSLATE_HOST = "https://api.fanyi.baidu.com/api/trans/vip/translate?";

    private List<String> languageList1;
    private List<String> languageList2;
    private List<String> collectionList;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    public void initData() {
        languageList1 = getLanguageList1();
        languageList2 = getLanguageList2();
    }

    public List<String> getLanguageList1() {
        if (languageList1 == null) {
            languageList1 = new ArrayList<>();
            Collections.addAll(languageList1, LanguageMap.getLanguages());
        }
        return languageList1;
    }

    public List<String> getLanguageList2() {
        if (languageList2 == null) {
            languageList2 = new ArrayList<>();
            languageList2.addAll(Arrays.asList(LanguageMap.getLanguages()).subList(1, LanguageMap.getLanguages().length));
        }
        return languageList2;
    }

    public List<String> getCollectionList(Context context) {
        if (collectionList == null) {
            collectionList = new ArrayList<>();
            Map<String, String> map = (Map<String, String>) getSharedPreferences(context).getAll();
            collectionList.addAll(map.keySet());
        }
        return collectionList;
    }

    public void addToMyFavourite(String source, String target, Context context) {
        editor = getEditor(context);
        String s = source + " > " + target;
        editor.putString(s, s);
        editor.commit();
    }

    public void sendRequest(String input, CharSequence from, CharSequence to, HttpUtil.Callback callback){
        String address = getAddress(input,from,to);
        HttpUtil.sendOkHttpRequest(address,callback);
    }

    @NotNull
    private String getAddress(String input, CharSequence from, CharSequence to) {
        from = LanguageMap.getShortCode(from);
        to = LanguageMap.getShortCode(to);
        String random = String.valueOf((int) (Math.random() * 1000000));
        String s = TRANSLATE_APP_ID + input + random + TRANSLATE_SECRET_KEY;
        String sign = EncryptionUtil.encryptionMD5(s.getBytes(), false);
        return TRANSLATE_HOST +
                "q=" + input +
                "&from=" + from +
                "&to=" + to +
                "&appid=" + TRANSLATE_APP_ID +
                "&salt=" + random +
                "&sign=" + sign;
    }

    public String handleTranslationResponse(@NotNull Response response) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(s);
            JSONObject jsonObject1 = jsonObject.getJSONArray("trans_result").getJSONObject(0);
            return jsonObject1.getString("dst");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SharedPreferences getSharedPreferences(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("MyFavourite", Context.MODE_PRIVATE);
            mSharedPreferences.registerOnSharedPreferenceChangeListener((sharedPreferences, key) -> getCollectionList(context).add(sharedPreferences.getString(key, "")));
        }
        return mSharedPreferences;
    }

    private SharedPreferences.Editor getEditor(Context context) {
        if (editor == null) {
            editor = getSharedPreferences(context).edit();
        }
        return editor;
    }
}
