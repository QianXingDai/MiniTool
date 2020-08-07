package com.kakacat.minitool.epidemicinquiry.model;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class Model {
    //疫情省市情况查询接口
    private static final String HOST = "http://api.tianapi.com/txapi/ncovcity/index";
    //疫情省市情况查询接口key
    private static final String KEY = "2675f5858d0ba338b6d8d4f93cfe17be";
    //疫情省市情况查询地址
    public static final String ADDRESS = HOST + "?key=" + KEY;

    private static Model model;
    private List<GroupBean> groupBeanList;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public List<GroupBean> getGroupBeanList() {
        if (groupBeanList == null) {
            groupBeanList = new ArrayList<>();
        }
        return groupBeanList;
    }

    public boolean handleResponse(@NotNull Response response) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = jsonObject.getJSONArray("newslist");
            Gson gson = new Gson();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                GroupBean groupBean = new GroupBean();

                groupBean.setLocation(jsonObject1.getString("provinceShortName"));
                groupBean.setCurrentConfirmCount(jsonObject1.getString("currentConfirmedCount"));
                groupBean.setTotalConfirmCount(jsonObject1.getString("confirmedCount"));
                groupBean.setSusPectCount(jsonObject1.getString("suspectedCount"));
                groupBean.setCuredCount(jsonObject1.getString("curedCount"));
                groupBean.setDeadCount(jsonObject1.getString("deadCount"));

                List<ChildBean> childBeanList = groupBean.getChildBeanList();
                JSONArray jsonArray1 = jsonObject1.getJSONArray("cities");
                for (int i1 = 0; i1 < jsonArray1.length(); i1++) {
                    ChildBean childBean = gson.fromJson(jsonArray1.getJSONObject(i1).toString(), ChildBean.class);
                    childBeanList.add(childBean);
                }
                getGroupBeanList().add(groupBean);
            }
            return true;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return false;
    }
}
