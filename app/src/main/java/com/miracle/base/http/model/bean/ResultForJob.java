package com.miracle.base.http.model.bean;

import java.io.Serializable;

public class ResultForJob<T> extends BaseEntityForJob implements Serializable{

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
