package com.miracle.base.http.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/23.
 */
public class PageEntity<T> implements Serializable {

    private int page_count;

    private List<T> list;

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
