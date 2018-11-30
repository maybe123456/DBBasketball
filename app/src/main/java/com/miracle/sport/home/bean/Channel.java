package com.miracle.sport.home.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class Channel implements MultiItemEntity, Serializable {
    public static final int TYPE_MY = 1;
    public static final int TYPE_OTHER = 2;
    public static final int TYPE_MY_CHANNEL = 3;
    public static final int TYPE_OTHER_CHANNEL = 4;

    public String name;
    public String id;
    public int itemType;
    public String class_id;
    public String pic;

//    public Channel(String title, String channelCode) {
//        this(TYPE_MY_CHANNEL, title, channelCode);
//    }
    public Channel(String title, String channelCode,String pic) {
        this(TYPE_MY_CHANNEL, title, channelCode , pic);
    }

//    public Channel(int type, String title, String channelCode) {
//        this.name = title;
//        this.id = channelCode;
//        itemType = type;
//    }
    public Channel(int type, String title, String channelCode,String pic) {
        this.name = title;
        this.id = channelCode;
        this.pic = pic;
        itemType = type;
    }



    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}