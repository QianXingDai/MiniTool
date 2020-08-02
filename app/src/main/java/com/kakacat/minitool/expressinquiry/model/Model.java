package com.kakacat.minitool.expressinquiry.model;

import android.content.Context;

import androidx.annotation.IntRange;

import com.google.gson.Gson;
import com.kakacat.minitool.common.myinterface.HttpCallback;
import com.kakacat.minitool.common.util.HttpUtil;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Model {

    private static final String KEY = "2675f5858d0ba338b6d8d4f93cfe17be";
    private static final String HOST = "http://api.tianapi.com/txapi/kuaidi/index";

    private static Model model;

    private List<Delivery> unSignedList;
    private List<Delivery> signedList;
    private List<Delivery> allList;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public void initData(Context context) {
        loadDataFromLocal(context);
        if (unSignedList == null) {
            unSignedList = new ArrayList<>();
        }
        if (signedList == null) {
            signedList = new ArrayList<>();
        }
        if (allList == null) {
            allList = new ArrayList<>();
        }
    }

    public void sendRequest(String code, HttpCallback callback) {
        String address = HOST +
                "?key=" + KEY +
                "&number=" + code;
        RequestBody requestBody = new FormBody.Builder()
                .add("key", KEY)
                .add("code", code)
                .build();

        HttpUtil.sendOkHttpRequest(address, callback, requestBody);
    }

    public boolean checkInput(String code) {
        for (char ch : code.toCharArray()) {
            if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    public boolean isInSignedList(String code) {
        for (Delivery delivery : signedList) {
            if (delivery.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public boolean handleResponse(Response response, String code) {
        Delivery delivery = handle(response, code);
        if (delivery == null) {
            return false;
        }

        boolean flag = false;
        for (Delivery delivery1 : allList) {
            if (delivery1.getCode().equals(code)) {
                flag = true;
                delivery1.setStatus(delivery.getStatus());
                delivery1.setPackageDetailList(delivery.getPackageDetailList());
                delivery1.setUpdateTime(delivery.getUpdateTime());
            }
        }
        if (!flag) {
            allList.add(delivery);
            if (delivery.getStatus().equals("已签收")) {
                signedList.add(delivery);
            } else {
                unSignedList.add(delivery);
            }
        } else {
            unSignedList.clear();
            unSignedList.addAll(allList.stream().filter(delivery1 -> !delivery1.getStatus().equals("已签收")).collect(Collectors.toList()));
            signedList.clear();
            signedList.addAll(allList.stream().filter(delivery1 -> delivery1.getStatus().equals("已签收")).collect(Collectors.toList()));
        }

        return true;
    }

    private Delivery handle(Response response, String code) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            JSONObject jsonObject = new JSONObject(s);

            JSONObject jsonObject1 = jsonObject.getJSONArray("newslist").getJSONObject(0);

            String status = jsonObject1.getString("status");
            String updateTime = jsonObject1.getString("updatetime");
            String companyName = jsonObject1.getString("kuaidiname");
            String tel = jsonObject1.getString("telephone");
            JSONArray jsonArray = jsonObject1.getJSONArray("list");
            List<PackageDetail> packageDetailList = new ArrayList<>();
            Gson gson = new Gson();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                packageDetailList.add(gson.fromJson(object.toString(), PackageDetail.class));
            }
            return new Delivery(status, companyName, code, tel, updateTime, packageDetailList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveDataToLocal(@NotNull Context context) {
        ObjectOutputStream oos1 = null;
        ObjectOutputStream oos2 = null;
        ObjectOutputStream oos3 = null;

        try {
            oos1 = new ObjectOutputStream(context.openFileOutput("delivery_unsigned", Context.MODE_PRIVATE));
            oos2 = new ObjectOutputStream(context.openFileOutput("delivery_signed", Context.MODE_PRIVATE));
            oos3 = new ObjectOutputStream(context.openFileOutput("delivery_all", Context.MODE_PRIVATE));

            oos1.writeObject(unSignedList);
            oos2.writeObject(signedList);
            oos3.writeObject(allList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos1 != null) oos1.close();
                if (oos2 != null) oos1.close();
                if (oos3 != null) oos1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDataFromLocal(@NotNull Context context) {
        ObjectInputStream ois1 = null;
        ObjectInputStream ois2 = null;
        ObjectInputStream ois3 = null;

        try {
            ois1 = new ObjectInputStream(context.openFileInput("delivery_unsigned"));
            ois2 = new ObjectInputStream(context.openFileInput("delivery_signed"));
            ois3 = new ObjectInputStream(context.openFileInput("delivery_all"));

            unSignedList = (List<Delivery>) ois1.readObject();
            signedList = (List<Delivery>) ois2.readObject();
            allList = (List<Delivery>) ois3.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois1 != null) ois1.close();
                if (ois2 != null) ois2.close();
                if (ois3 != null) ois3.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<Delivery> getList(@IntRange(from = 0, to = 2) int position) {
        if (position == 0) {
            return unSignedList;
        } else if (position == 1) {
            return signedList;
        } else if (position == 2) {
            return allList;
        }
        return null;
    }
}
