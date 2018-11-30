package com.miracle.michael.football.bean;

import java.io.Serializable;

public class FootballClubBean implements Serializable {

    /**
     * id : zcsj
     * name : 中超比分
     */

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
