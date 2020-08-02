package com.kakacat.minitool.expressinquiry.model;

import java.io.Serializable;

public class PackageDetail implements Serializable {

    private String time;
    private String content;

    public PackageDetail(String time, String content) {
        setTime(time);
        setContent(content);
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content.trim();
    }
}
