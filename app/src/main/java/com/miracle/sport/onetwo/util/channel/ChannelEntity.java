package com.miracle.sport.onetwo.util.channel;

/**
 * 频道实体类
 * Created by YoKeyword on 15/12/29.
 */
public class ChannelEntity {

    private long id;
    private String name;
    private String type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChannelEntity() {
    }

    public ChannelEntity(long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
