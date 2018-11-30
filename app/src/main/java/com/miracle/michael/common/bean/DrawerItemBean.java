package com.miracle.michael.common.bean;

/**
 * Created by Michael on 2018/10/18 20:18 (星期四)
 */
public class DrawerItemBean {
    private String icon;
    private String name;

    public DrawerItemBean(String icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
