package com.miracle.sport.community.bean;

import java.io.Serializable;

/**
 * Created by Michael on 2018/10/31 19:31 (星期三)
 */
public class MyCircleBean  implements Serializable {

    /**
     * name : 德甲
     * id : 8
     */

    private String name;
    private int id;
    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
