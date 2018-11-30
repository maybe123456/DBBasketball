package com.miracle.sport.schedule.bean;


public class ClubeType {

    /**
     * id : 1
     * name : 中超
     */

    private int id;
    private String name;
    private String pic;

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

    public ClubeType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public ClubeType() {
    }
}
