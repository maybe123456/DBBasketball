package com.miracle.base.http.model.bean;


import java.io.Serializable;
import java.util.List;

public class PageResultForJob<T> extends BaseEntityForJob implements Serializable {

    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
