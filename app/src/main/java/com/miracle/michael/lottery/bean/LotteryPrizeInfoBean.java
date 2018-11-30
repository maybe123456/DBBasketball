package com.miracle.michael.lottery.bean;

/**
 * Created by asus on 2018/4/18.
 */

public class LotteryPrizeInfoBean {

    /**
     * gameUniqueId : HF_BJPK10
     * openCode : 08,09,06,07,05,01,02,03,10,04
     * openTime : 2018-04-18T11:22:44+08:00
     * uniqueIssueNumber : 677151
     * planNo : 677151
     * openStatus : true
     * gameNameInChinese : 北京PK拾
     * officialOpenTime : 2018-04-18T11:22:00+08:00
     * officialOpenTimeEpoch : 1524021720
     * nextOfficialOpenTime : 2018-04-18T11:27:00+08:00
     * nextOfficialOpenTimeEpoch : 1524022020
     * nextStopOrderTime : 2018-04-18T11:26:30+08:00
     * nextStopOrderTimeEpoch : 1524021990
     * stopOrderTime : 2018-04-18T11:21:30+08:00
     * stopOrderTimeEpoch : 1524021690
     * currentTimeEpoch : 1524021687
     */

    private String gameUniqueId;
    private String openCode;
    private String openTime;
    private String uniqueIssueNumber;
    private String planNo;
    private boolean openStatus;
    private String gameNameInChinese;
    private String officialOpenTime;
    private long officialOpenTimeEpoch;
    private String nextOfficialOpenTime;
    private String nextOfficialOpenTimeEpoch;
    private String nextStopOrderTime;
    private String nextStopOrderTimeEpoch;
    private String stopOrderTime;
    private String stopOrderTimeEpoch;
    private String currentTimeEpoch;

    public String getGameUniqueId() {
        return gameUniqueId;
    }

    public void setGameUniqueId(String gameUniqueId) {
        this.gameUniqueId = gameUniqueId;
    }

    public String getOpenCode() {
        return openCode;
    }

    public void setOpenCode(String openCode) {
        this.openCode = openCode;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getUniqueIssueNumber() {
        return uniqueIssueNumber;
    }

    public void setUniqueIssueNumber(String uniqueIssueNumber) {
        this.uniqueIssueNumber = uniqueIssueNumber;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public boolean isOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(boolean openStatus) {
        this.openStatus = openStatus;
    }

    public String getGameNameInChinese() {
        return gameNameInChinese;
    }

    public void setGameNameInChinese(String gameNameInChinese) {
        this.gameNameInChinese = gameNameInChinese;
    }

    public String getOfficialOpenTime() {
        return officialOpenTime;
    }

    public void setOfficialOpenTime(String officialOpenTime) {
        this.officialOpenTime = officialOpenTime;
    }

    public long getOfficialOpenTimeEpoch() {
        return officialOpenTimeEpoch;
    }

    public void setOfficialOpenTimeEpoch(long officialOpenTimeEpoch) {
        this.officialOpenTimeEpoch = officialOpenTimeEpoch;
    }

    public String getNextOfficialOpenTime() {
        return nextOfficialOpenTime;
    }

    public void setNextOfficialOpenTime(String nextOfficialOpenTime) {
        this.nextOfficialOpenTime = nextOfficialOpenTime;
    }

    public String getNextOfficialOpenTimeEpoch() {
        return nextOfficialOpenTimeEpoch;
    }

    public void setNextOfficialOpenTimeEpoch(String nextOfficialOpenTimeEpoch) {
        this.nextOfficialOpenTimeEpoch = nextOfficialOpenTimeEpoch;
    }

    public String getNextStopOrderTime() {
        return nextStopOrderTime;
    }

    public void setNextStopOrderTime(String nextStopOrderTime) {
        this.nextStopOrderTime = nextStopOrderTime;
    }

    public String getNextStopOrderTimeEpoch() {
        return nextStopOrderTimeEpoch;
    }

    public void setNextStopOrderTimeEpoch(String nextStopOrderTimeEpoch) {
        this.nextStopOrderTimeEpoch = nextStopOrderTimeEpoch;
    }

    public String getStopOrderTime() {
        return stopOrderTime;
    }

    public void setStopOrderTime(String stopOrderTime) {
        this.stopOrderTime = stopOrderTime;
    }

    public String getStopOrderTimeEpoch() {
        return stopOrderTimeEpoch;
    }

    public void setStopOrderTimeEpoch(String stopOrderTimeEpoch) {
        this.stopOrderTimeEpoch = stopOrderTimeEpoch;
    }

    public String getCurrentTimeEpoch() {
        return currentTimeEpoch;
    }

    public void setCurrentTimeEpoch(String currentTimeEpoch) {
        this.currentTimeEpoch = currentTimeEpoch;
    }
}
