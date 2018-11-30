package com.miracle.sport.schedule.bean.post;
//射手
public class ClubePostSC{

    /**
     * id : 38
     * fixture : 2018-10-20 13:00:00
     * home_pic : https://img.dongqiudi.com/data/pic/1241.png
     * home : 罗马
     * guest_pic : https://img.dongqiudi.com/data/pic/1287.png
     * guest : 斯帕尔
     * rotation : 0:2
     * league : 5
     */

    private int id;
    private String fixture;
    private String home_pic;
    private String home;
    private String guest_pic;
    private String guest;
    private String rotation;
    private String league;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFixture() {
        return fixture;
    }

    public void setFixture(String fixture) {
        this.fixture = fixture;
    }

    public String getHome_pic() {
        return home_pic;
    }

    public void setHome_pic(String home_pic) {
        this.home_pic = home_pic;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest_pic() {
        return guest_pic;
    }

    public void setGuest_pic(String guest_pic) {
        this.guest_pic = guest_pic;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getRotation() {
        return rotation;
    }

    public void setRotation(String rotation) {
        this.rotation = rotation;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}
