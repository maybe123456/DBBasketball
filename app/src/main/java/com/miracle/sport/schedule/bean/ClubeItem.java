package com.miracle.sport.schedule.bean;

public class ClubeItem {

    /**
     * type : jf
     * name : 积分榜
     */

    public static final String TYPE_JF = "jf"; //积分
    public static final String TYPE_SS = "ss"; //射手
    public static final String TYPE_ZG = "zg"; //助攻
    public static final String TYPE_SC = "sc"; //赛程

    private String type;
    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
