package com.kakacat.minitool.translation;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kakacat.minitool.common.constant.AppKey;
import com.kakacat.minitool.common.constant.Host;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.EncryptionUtil;
import com.kakacat.minitool.common.util.HttpUtil;

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

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Context context;

    private List<String> languageList1;
    private List<String> languageList2;

    public Presenter(Contract.View view) {
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void initData() {
        languageList1 = getLanguageList1();
        languageList2 = getLanguageList2();
    }

    @Override
    public List<String> getLanguageList1(){
        if(languageList1 == null){
            languageList1 = new ArrayList<>();
            Collections.addAll(languageList1,LanguageMap.getLanguages());
        }
        return languageList1;
    }

    @Override
    public List<String> getLanguageList2(){
        if(languageList2 == null){
            languageList2 = new ArrayList<>();
            languageList2.addAll(Arrays.asList(LanguageMap.getLanguages()).subList(1, LanguageMap.getLanguages().length));
        }
        return languageList2;
    }

    @Override
    public List<String> getCollectionList(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyFavourite",Context.MODE_PRIVATE);
        Map<String,String> map = (Map<String, String>) sharedPreferences.getAll();
        return new ArrayList<>(map.keySet());
    }

    @Override
    public void addToMyFavourite(String source,String target) {
        if(TextUtils.isEmpty(source)){
            view.onAddToMyFavouriteCallBack("请输入内容");
        }else{
            SharedPreferences sharedPreferences = context.getSharedPreferences("MyFavourite",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String s = source + ">" + target;
            editor.putString(s,s);
            editor.commit();
            view.onAddToMyFavouriteCallBack("收藏成功");
        }
    }

    @Override
    public void requestData(String input,CharSequence from,CharSequence to){
        if(TextUtils.isEmpty(input)){
            view.onRequestCallBack(null, Result.INPUT_ERROR);
            return;
        }
        String address = getAddress(input,from,to);
        HttpUtil.sendOkHttpRequest(address, new HttpCallback() {
            int resultFlag = Result.REQUEST_ERROR;
            @Override
            public void onSuccess(Response response) {
                String s = handleTranslationResponse(response);
                if(TextUtils.isEmpty(s)){
                    resultFlag = Result.HANDLE_FAIL;
                }else{
                    resultFlag = Result.HANDLE_SUCCESS;
                }
                view.onRequestCallBack(s,resultFlag);
            }

            @Override
            public void onError() {
                view.onRequestCallBack(null,Result.REQUEST_ERROR);
            }
        });
    }

    private String getAddress(String input,CharSequence from,CharSequence to){
        from = LanguageMap.getShortCode(from);
        to = LanguageMap.getShortCode(to);
        String random = String.valueOf((int) (Math.random() * 1000000));
        String s = AppKey.TRANSLATE_APP_ID + input + random + AppKey.TRANSLATE_SECRET_KEY;
        String sign = EncryptionUtil.encryptionMD5(s.getBytes(),false);
        return Host.TRANSLATE_HOST +
                "q=" + input +
                "&from=" + from +
                "&to=" + to +
                "&appid=" + AppKey.TRANSLATE_APP_ID +
                "&salt=" + random +
                "&sign=" + sign;
    }

    @Override
    public String handleTranslationResponse(Response response){
        try{
            String s = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(s);
            JSONObject jsonObject1 = jsonObject.getJSONArray("trans_result").getJSONObject(0);
            return jsonObject1.getString("dst");
        }catch (IOException | JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
