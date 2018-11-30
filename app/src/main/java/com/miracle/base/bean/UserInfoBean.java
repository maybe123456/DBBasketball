package com.miracle.base.bean;

import java.io.Serializable;

public class UserInfoBean implements Serializable {

    /**
     * id : 4
     * nickname : 小白
     * username : 13188888888
     * phone : 0
     * emall : 0
     * img : 9.988lhkj.com/app/imgcom.asdad.cdcxc/10//Mr69aNHfqkUDvn35pu3LqSkVwgqNpbtn5ZrTPixy.png
     */

    private int id;
    private String nickname;
    private String username;
    private int phone;
    private String emall;
    private String img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmall() {
        return emall;
    }

    public void setEmall(String emall) {
        this.emall = emall;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
