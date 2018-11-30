package com.miracle.sport.home.bean;

import java.util.List;

public class HomeBean {

    private List<Football> data;
    private List<Football> children;

    public List<Football> getData() {
        return data;
    }

    public void setData(List<Football> data) {
        this.data = data;
    }

    public List<Football> getChildren() {
        return children;
    }

    public void setChildren(List<Football> children) {
        this.children = children;
    }
}
