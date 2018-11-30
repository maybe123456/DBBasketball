package com.miracle.michael.common.bean;

import java.io.Serializable;

public class CommentChildBean implements Serializable {


    /**
     * content : 对评论评论
     * add_time : 2018-10-31 14:53:01
     * nickname : 恶魔
     * user_id : 2
     * to_name : null
     * to_user_id : null
     */

    private String content;
    private String add_time;
    private String nickname;
    private int user_id;
    private String to_name;
    private String to_user_id;

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

    public String getTo_name() {
        return to_name;
    }


    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_name(String to_name) {
        this.to_name = to_name;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }
}
