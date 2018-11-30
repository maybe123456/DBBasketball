package com.miracle.sport.schedule.bean.post;
//赛事
public class ClubePostSS {

    /**
     * id : 839
     * ranking : 1
     * player_pic : https://img.dongqiudi.com/data/personpic/342295.png
     * player_name : 皮扬特克
     * club_pic : https://img.dongqiudi.com/data/pic/1276.png
     * club_name : 热那亚
     * amount : 9
     * league : 5
     * list_type : 1
     */



    private int id;
    private int ranking;
    private String player_pic;
    private String player_name;
    private String club_pic;
    private String club_name;
    private int amount;
    private String league;
    private String list_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getPlayer_pic() {
        return player_pic;
    }

    public void setPlayer_pic(String player_pic) {
        this.player_pic = player_pic;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getClub_pic() {
        return club_pic;
    }

    public void setClub_pic(String club_pic) {
        this.club_pic = club_pic;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getList_type() {
        return list_type;
    }

    public void setList_type(String list_type) {
        this.list_type = list_type;
    }
}
