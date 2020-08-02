package com.kakacat.minitool.inquireip;

import android.text.TextUtils;

import com.kakacat.minitool.common.util.SystemUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpBean {
    private String country;
    private String province;
    private String city;
    private String isp;
    private String ipAddress;

    public IpBean() {
    }

    public static boolean checkIp(String input) {
        if (TextUtils.isEmpty(input)) {
            input = SystemUtil.getLocalIPAddress();
            if (TextUtils.isEmpty(input)) {
                return false;
            }
        }
        //检查ip格式
        String regex =
                "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                        + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(regex);
        assert input != null;
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
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


}
