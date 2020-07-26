package com.kakacat.minitool.phoneartribution;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Response;

public class PhoneNumber {

    private String number;
    private String province;
    private String city;
    private String areaCode;
    private String zip;
    private String company;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public static PhoneNumber response2PhoneNumber(Response response){
        try {
            String s = Objects.requireNonNull(response.body()).string();
            if(!TextUtils.isEmpty(s)){
                JSONObject jsonObject = new JSONObject(s);
                JSONObject result = jsonObject.getJSONObject("result");
                PhoneNumber phoneNumber = new PhoneNumber();
                phoneNumber.setProvince(result.getString("province"));
                phoneNumber.setCity(result.getString("city"));
                phoneNumber.setAreaCode(result.getString("areacode"));
                phoneNumber.setZip(result.getString("zip"));
                phoneNumber.setCompany(result.getString("company"));
                return phoneNumber;
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
