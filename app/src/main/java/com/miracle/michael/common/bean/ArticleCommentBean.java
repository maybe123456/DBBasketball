package com.miracle.michael.common.bean;

import java.io.Serializable;
import java.util.List;

public class ArticleCommentBean implements Serializable {


    private int comment_id;
    private String content;
    private String add_time;
    private String nickname;
    private int user_id;
    private String img;
    private int create_id;
    private int click_num;
    private int comment_click_num;
    private int click;
    private int child_count;
    private List<CommentChildBean> child;

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCreate_id() {
        return create_id;
    }

    public void setCreate_id(int create_id) {
        this.create_id = create_id;
    }

    public int getClick_num() {
        return click_num;
    }

    public void setClick_num(int click_num) {
        this.click_num = click_num;
    }

    public int getComment_click_num() {
        return comment_click_num;
    }

    public void setComment_click_num(int comment_click_num) {
        this.comment_click_num = comment_click_num;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

    public int getChild_count() {
        return child_count;
    }

    public void setChild_count(int child_count) {
        this.child_count = child_count;
    }

    public List<CommentChildBean> getChild() {
        return child;
    }

    public void setChild(List<CommentChildBean> child) {
        this.child = child;
    }
}
