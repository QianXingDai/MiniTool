package com.kakacat.minitool.inquireip;

import android.text.TextUtils;

import com.kakacat.minitool.common.util.SystemUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

public class IpModel {
    private String country;
    private String province;
    private String city;
    private String isp;
    private String ipAddress;

    public IpModel() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public static boolean checkIp(String input){
        if(TextUtils.isEmpty(input)){
            input = SystemUtil.getLocalIPAddress();
            if(TextUtils.isEmpty(input)){
                return false;
            }
        }
        //检查ip格式
        String regex =
                "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(regex);
        assert input != null;
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static IpModel handleIpDataResponse(Response response){
        try {
            String s = Objects.requireNonNull(response.body()).string();
            if(TextUtils.isEmpty(s)){
                return null;
            }
            IpModel ipModel = new IpModel();
            JSONObject jsonObject = new JSONObject(s);
            if(!jsonObject.getString("resultcode").equals("200")){
                return null;
            }
            JSONObject result = jsonObject.getJSONObject("result");
            ipModel.setCountry(result.getString("Country"));
            ipModel.setProvince(result.getString("Province"));
            ipModel.setCity(result.getString("City"));
            ipModel.setIsp(result.getString("Isp"));

            return ipModel;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
