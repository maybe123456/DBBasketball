package com.miracle.sport.community.bean;

import java.util.List;

/**
 * Created by Michael on 2018/10/27 15:43 (星期六)
 */
public class PostBean {


    /**
     * id : 20
     * title : fuu
     * thumb : ["http://xiaozhuang.988lhkj.comuploads/20181030/f5bfada557e243bce76702e788e33fc5.jpg","http://xiaozhuang.988lhkj.comuploads/20181030/9de6a0c0e69e20692d83eafb754d9705.jpg","http://xiaozhuang.988lhkj.comuploads/20181030/c58fe0a0c7128c68a874179b273a827a.png"]
     * content : fjcjcj
     * click_num : 0
     * comment_num : 0
     * total : 0
     * add_time : 2018-10-30 20:47:56
     * nickname : Michael
     * name : 湖人
     */

    private int id;
    private String title;
    private String content;
    private int click;
    private int click_num;
    private int comment_num;
    private int total;
    private String add_time;
    private String nickname;
    private String img;
    private String name;
    private List<String> thumb;

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getThumb() {
        return thumb;
    }

    public void setThumb(List<String> thumb) {
        this.thumb = thumb;
    }
}
