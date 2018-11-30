package com.miracle.sport.onetwo.netbean;

import java.util.List;

public class FishType {

    /**
     * id : 1
     * name : 精彩推荐
     * child : []
     */

    private int id;
    private String name;
    private String pic = "";
    private List<FishType> child;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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

    public List<FishType> getChild() {
        return child;
    }

    public void setChild(List<FishType> child) {
        this.child = child;
    }
}
