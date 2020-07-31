package com.kakacat.minitool.epidemicinquiry.model;

public class ChildBean {

    private String cityName;
    private String currentConfirmedCount;
    private String confirmedCount;
    private String susPectedCount;
    private String curedCount;
    private String deadCount;
    private String locationId;

    public ChildBean(String cityName, String currentConfirmedCount, String confirmedCount, String susPectedCount, String curedCount, String deadCount, String locationId) {
        this.cityName = cityName;
        this.currentConfirmedCount = currentConfirmedCount;
        this.confirmedCount = confirmedCount;
        this.susPectedCount = susPectedCount;
        this.curedCount = curedCount;
        this.deadCount = deadCount;
        this.locationId = locationId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCurrentConfirmedCount() {
        return currentConfirmedCount;
    }

    public void setCurrentConfirmedCount(String currentConfirmedCount) {
        this.currentConfirmedCount = currentConfirmedCount;
    }

    public String getConfirmedCount() {
        return confirmedCount;
    }

    public void setConfirmedCount(String confirmedCount) {
        this.confirmedCount = confirmedCount;
    }

    public String getSusPectedCount() {
        return susPectedCount;
    }

    public void setSusPectedCount(String susPectedCount) {
        this.susPectedCount = susPectedCount;
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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }
}
