package com.miracle.sport.onetwo.netbean;

import java.io.Serializable;

public class LotteryCatTitleItem implements Serializable {
    int id;
    String name;
    String pic;

    @Override
    public String toString() {
        return "LotteryCatTitleItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
