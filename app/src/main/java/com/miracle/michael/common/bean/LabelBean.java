package com.miracle.michael.common.bean;

/**
 * Created by Michael on 2018/10/26 16:18 (星期五)
 */
public class LabelBean {
    private String text;
    private boolean show;

    public LabelBean(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
