package com.kakacat.minitool.epidemicinquiry.model;

import java.util.ArrayList;
import java.util.List;

public class GroupBean {

    private String location;
    private String currentConfirmCount;
    private String totalConfirmCount;
    private String susPectCount;
    private String curedCount;
    private String deadCount;
    private List<ChildBean> childBeanList;

    public GroupBean() {

    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCurrentConfirmCount() {
        return currentConfirmCount;
    }

    public void setCurrentConfirmCount(String currentConfirmCount) {
        this.currentConfirmCount = currentConfirmCount;
    }

    public String getTotalConfirmCount() {
        return totalConfirmCount;
    }

    public void setTotalConfirmCount(String totalConfirmCount) {
        this.totalConfirmCount = totalConfirmCount;
    }

    public String getSusPectCount() {
        return susPectCount;
    }

    public void setSusPectCount(String susPectCount) {
        this.susPectCount = susPectCount;
    }

    public String getCuredCount() {
        return curedCount;
    }

    public void setCuredCount(String curedCount) {
        this.curedCount = curedCount;
    }

    public String getDeadCount() {
        return deadCount;
    }

    public void setDeadCount(String deadCount) {
        this.deadCount = deadCount;
    }

    public List<ChildBean> getChildBeanList() {
        if(childBeanList == null){
            childBeanList = new ArrayList<>();
        }
        return childBeanList;
    }

    public void setChildBeanList(List<ChildBean> childBeanList) {
        this.childBeanList = childBeanList;
    }

}
