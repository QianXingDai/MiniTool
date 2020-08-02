package com.kakacat.minitool.expressinquiry.model;

import java.io.Serializable;
import java.util.List;

public class Delivery implements Serializable {

    private String status;
    private String companyName;
    private String code;
    private String tel;
    private String updateTime;
    private List<PackageDetail> packageDetailList;

    public Delivery(String status, String companyName, String code, String tel, String updateTime, List<PackageDetail> packageDetailList) {
        this.status = status;
        this.companyName = companyName;
        this.code = code;
        this.tel = tel;
        this.updateTime = updateTime;
        this.packageDetailList = packageDetailList;
    }


    public String getStatus() {
        switch (Integer.parseInt(status)) {
            case -1:
                return "待查询";
            case 0:
                return "查询异常";
            case 1:
                return "暂无记录";
            case 2:
                return "在途中";
            case 3:
                return "在派送";
            case 4:
                return "已签收";
            case 5:
                return "用户拒签";
            case 6:
                return "疑难件";
            case 7:
                return "无效单";
            case 8:
                return "超时单";
            case 9:
                return "签收失败";
            case 10:
                return "退回";
        }
        return "";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCode() {
        return code;
    }

    public String getTel() {
        return tel;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<PackageDetail> getPackageDetailList() {
        return packageDetailList;
    }

    public void setPackageDetailList(List<PackageDetail> packageDetailList) {
        this.packageDetailList = packageDetailList;
    }

}
