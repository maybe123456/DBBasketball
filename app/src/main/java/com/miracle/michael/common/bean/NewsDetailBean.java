package com.miracle.michael.common.bean;

import java.io.Serializable;

/**
 * Created by Michael on 2018/10/17 15:32 (星期三)
 */
public class NewsDetailBean implements Serializable {

    /**
     * id : 1
     * title : 2015WPT中国赛6人决赛桌诞生 张伟成为最大泡沫
     * class_id : 1
     * content :
     * <div class="text clear" id="contentText">
     * <div itemprop="articleBody"><div align="left">
     * <div align="center"><img src="http://photocdn.sohu.com/20151102/Img424930807.png" alt="2015WPT中国赛9人决赛桌，1号位 蔡世杰，2号位 孙树，3号位 李超，4号位 金文龙，5号位 金鑫，6号位 王城新，7号位 张伟，8号位
     * author : 搜狐棋牌
     * time : 2015-11-02 11:08:49
     * thumb : http://photocdn.sohu.com/20151102/Img424930807.png
     * cion : 0
     */

    private int id;
    private String title;
    private int class_id;
    private String content;
    private String author;
    private String time;
    private String thumb;
    private int coin;

    private int click_num;
    private int click;
    private int comment_num;

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

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getCoin() {
        return coin;
    }

    public void setCion(int coin) {
        this.coin = coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
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
}
