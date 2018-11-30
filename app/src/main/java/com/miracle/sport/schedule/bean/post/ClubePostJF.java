package com.miracle.sport.schedule.bean.post;
//积分
public class ClubePostJF {


    /**
     * id : 5
     * ranking : 1
     * club_pic : https://img.dongqiudi.com/soccer/data/logo/team/1040.png
     * club_name : 尤文图斯
     * fields : 9
     * win : 8
     * draw : 1
     * loss : 0
     * goal : 19
     * fumble : 6
     * goal_diff : 13
     * integral : 25
     * league : 5
     */

    private int id;
    private int ranking;
    private String club_pic;
    private String club_name;
    private int fields;
    private int win;
    private int draw;
    private int loss;
    private int goal;
    private int fumble;
    private int goal_diff;
    private int integral;
    private String league;

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

    public int getFields() {
        return fields;
    }

    public void setFields(int fields) {
        this.fields = fields;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLoss() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getFumble() {
        return fumble;
    }

    public void setFumble(int fumble) {
        this.fumble = fumble;
    }

    public int getGoal_diff() {
        return goal_diff;
    }

    public void setGoal_diff(int goal_diff) {
        this.goal_diff = goal_diff;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}