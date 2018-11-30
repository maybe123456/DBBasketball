package com.miracle.sport.onetwo.entity;

public class CpHall {

    /**
     * id : 1
     * name : 双色球
     * period : 第2018119期
     * open_time : 昨天(周四)
     * red_num : 03 13 14 16 25 27
     * blue_num : 12
     * class_id : 1
     */

    private int id;
    private String name;
    private String period;
    private String open_time;
    private String red_num;
    private String blue_num;
    private String host_num;
    private String first_num;
    private int class_id;

    public String getHost_num() {
        return host_num;
    }

    public void setHost_num(String host_num) {
        this.host_num = host_num;
    }

    public String getFirst_num() {
        return first_num;
    }

    public void setFirst_num(String first_num) {
        this.first_num = first_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getOpen_time() {
        return open_time;
    }

    public void setOpen_time(String open_time) {
        this.open_time = open_time;
    }

    public String getRed_num() {
        return red_num;
    }

    public void setRed_num(String red_num) {
        this.red_num = red_num;
    }

    public String getBlue_num() {
        return blue_num;
    }

    public void setBlue_num(String blue_num) {
        this.blue_num = blue_num;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
}
