package com.kakacat.minitool.translation.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.kakacat.minitool.common.constant.AppKey;
import com.kakacat.minitool.common.constant.Host;
import com.kakacat.minitool.common.util.EncryptionUtil;

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

    private static Model model;

    private List<String> languageList1;
    private List<String> languageList2;
    private List<String> collectionList;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;

    private Model() {
    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

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

    public String getAddress(String input, CharSequence from, CharSequence to) {
        from = LanguageMap.getShortCode(from);
        to = LanguageMap.getShortCode(to);
        String random = String.valueOf((int) (Math.random() * 1000000));
        String s = AppKey.TRANSLATE_APP_ID + input + random + AppKey.TRANSLATE_SECRET_KEY;
        String sign = EncryptionUtil.encryptionMD5(s.getBytes(), false);
        return Host.TRANSLATE_HOST +
                "q=" + input +
                "&from=" + from +
                "&to=" + to +
                "&appid=" + AppKey.TRANSLATE_APP_ID +
                "&salt=" + random +
                "&sign=" + sign;
    }

    public String handleTranslationResponse(Response response) {
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
