package com.kakacat.minitool.currencyconversion.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kakacat.minitool.R;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Response;

public class Model {

    private static final String EXCHANGE_RATE_HOST = "http://web.juhe.cn:8080/finance/exchange/rmbquot?key=";
    private static final String EXCHANGE_RATE_KEY = "6103d9a9aeca9c09fc8f6bd4734be680";
    public static final String REQUEST_ADDRESS = EXCHANGE_RATE_HOST + EXCHANGE_RATE_KEY;

    private static Model model;
    private List<CountryBean> countryBeanList;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public void readRateFromLocal(@NotNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("rate", Context.MODE_PRIVATE);
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

    public List<CountryBean> getCountryBeanList() {
        if (countryBeanList == null) {
            fillCountryList();
        }
        return countryBeanList;
    }

    public boolean handleResponse(@NotNull Response response) {
        try {
            String s = Objects.requireNonNull(response.body()).string();
            if (!TextUtils.isEmpty(s)) {
                return false;
            } else {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject result = jsonObject.getJSONArray("result").getJSONObject(0);
                for (int i = 1; i <= 22; i++) {
                    JSONObject data = result.getJSONObject("data" + i);
                    double rate = Double.parseDouble(data.getString("bankConversionPri")) / 100;
                    Rate.setRate(rate, i);
                }
                return true;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getResult(CharSequence val, double rate1, double rate2) {
        if (TextUtils.isEmpty(val)) {
            return "";
        }
        double s = Double.parseDouble(val.toString());
        s = s * rate1 / rate2;
        return String.valueOf(s);
    }

    private void fillCountryList() {
        countryBeanList = new ArrayList<>();

        countryBeanList.add(new CountryBean(R.drawable.ic_us, R.string.name_us, R.string.unit_us, Rate.us));
        countryBeanList.add(new CountryBean(R.drawable.ic_eu, R.string.name_eu, R.string.unit_eu, Rate.eu));
        countryBeanList.add(new CountryBean(R.drawable.ic_hk, R.string.name_hk, R.string.unit_hk, Rate.hk));
        countryBeanList.add(new CountryBean(R.drawable.ic_jp, R.string.name_jp, R.string.unit_jp, Rate.jp));
        countryBeanList.add(new CountryBean(R.drawable.ic_uk, R.string.name_uk, R.string.unit_uk, Rate.uk));
        countryBeanList.add(new CountryBean(R.drawable.ic_au, R.string.name_au, R.string.unit_au, Rate.au));
        countryBeanList.add(new CountryBean(R.drawable.ic_ca, R.string.name_ca, R.string.unit_ca, Rate.ca));
        countryBeanList.add(new CountryBean(R.drawable.ic_th, R.string.name_th, R.string.unit_th, Rate.th));
        countryBeanList.add(new CountryBean(R.drawable.ic_sg, R.string.name_sg, R.string.unit_sg, Rate.sg));
        countryBeanList.add(new CountryBean(R.drawable.ic_ch, R.string.name_ch, R.string.unit_ch, Rate.ch));
        countryBeanList.add(new CountryBean(R.drawable.ic_dk, R.string.name_dk, R.string.unit_dk, Rate.dk));
        countryBeanList.add(new CountryBean(R.drawable.ic_ma, R.string.name_ma, R.string.unit_ma, Rate.ma));
        countryBeanList.add(new CountryBean(R.drawable.ic_my, R.string.name_my, R.string.unit_my, Rate.my));
        countryBeanList.add(new CountryBean(R.drawable.ic_no, R.string.name_no, R.string.unit_no, Rate.no));
        countryBeanList.add(new CountryBean(R.drawable.ic_nz, R.string.name_nz, R.string.unit_nz, Rate.nz));
        countryBeanList.add(new CountryBean(R.drawable.ic_ph, R.string.name_ph, R.string.unit_ph, Rate.ph));
        countryBeanList.add(new CountryBean(R.drawable.ic_ru, R.string.name_ru, R.string.unit_ru, Rate.ru));
        countryBeanList.add(new CountryBean(R.drawable.ic_se, R.string.name_se, R.string.unit_se, Rate.se));
        countryBeanList.add(new CountryBean(R.drawable.ic_tw, R.string.name_tw, R.string.unit_tw, Rate.tw));
        countryBeanList.add(new CountryBean(R.drawable.ic_br, R.string.name_br, R.string.unit_br, Rate.br));
        countryBeanList.add(new CountryBean(R.drawable.ic_kr, R.string.name_kr, R.string.unit_kr, Rate.kr));
        countryBeanList.add(new CountryBean(R.drawable.ic_za, R.string.name_za, R.string.unit_za, Rate.za));
        countryBeanList.add(new CountryBean(R.drawable.ic_cn, R.string.name_cn, R.string.unit_cn, Rate.cn));
    }
}
