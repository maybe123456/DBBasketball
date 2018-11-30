package com.miracle.michael.football.bean;

import java.io.Serializable;

/**
 * Created by Michael on 2018/10/21 20:16 (星期日)
 */
public class FootballF5ItemBean implements Serializable {
    private int img;
    private String name;
    private String reqKey;

    public FootballF5ItemBean(int img, String name, String reqKey) {
        this.img = img;
        this.name = name;
        this.reqKey = reqKey;
    }

    public String getReqKey() {
        return reqKey;
    }

    public void setReqKey(String reqKey) {
        this.reqKey = reqKey;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
