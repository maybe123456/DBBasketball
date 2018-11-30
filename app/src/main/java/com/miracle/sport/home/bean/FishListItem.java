package com.miracle.sport.home.bean;

import java.util.List;

public class FishListItem {

    /**
     * id : 95
     * title : 捕鱼达人特殊子弹最佳发射场景介绍
     * author : 小蛋
     * thumb : http://img1.gamedog.cn/2016/08/18/275-160QQ533530.jpg
     * time : 2016-08-18
     * click_num : 3
     * comment_num : 4
     * images : ["http://img1.gamedog.cn/2016/08/18/275-160QQ533530.jpg"]
     * click : 1
     */

    private int id;
    private String title;
    private String author;
    private String thumb;
    private String time;
    private int click_num;
    private int comment_num;
    private int click;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getClick_num() {
        return click_num;
    }

    public void setClick_num(int click_num) {
        this.click_num = click_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
