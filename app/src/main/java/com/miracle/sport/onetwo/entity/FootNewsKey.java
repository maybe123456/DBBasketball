package com.miracle.sport.onetwo.entity;

public class FootNewsKey extends CollecableAbs {
    private String key;
    private String name;

    private boolean LOCAL_isCollected = true;
    public boolean isLOCAL_isCollected() {
        return LOCAL_isCollected;
    }

    public void setLOCAL_isCollected(boolean LOCAL_isCollected) {
        this.LOCAL_isCollected = LOCAL_isCollected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String reqKey) {
        this.key = reqKey;
    }

}
