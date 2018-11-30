package com.miracle.michael.chess.bean;

/**
 * Created by Michael on 2018/10/17 15:25 (星期三)
 */
public class ChessNewsBean {

    /**
     * id : 4
     * title : 在12个城市海选！GPL全球扑克联赛中国站将启动
     * class_id : 1
     * author : 搜狐棋牌
     * time : 2017-05-26 13:29:59
     * thumb : http://photocdn.sohu.com/20170526/Img494563682.jpg
     */

    private int id;
    private String title;
    private int class_id;
    private String author;
    private String time;
    private String thumb;

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
}
