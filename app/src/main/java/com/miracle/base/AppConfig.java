package com.miracle.base;

import com.miracle.base.switcher.DBBean;
import com.miracle.michael.lottery.activity.LotteryMainActivity;
import com.miracle.sport.SportMainActivity;

public class AppConfig {
    public static String USER_ID = "";
    public static String APP_ID = "";
    public static String groupId;
    public static DBBean.DataBean DBENTITY;

    //1(足球) 2(彩票) 3(棋牌,斗地主) 4(生活)
    public static String APP_TYPE = "2";
    static Class mainClass = SportMainActivity.class;


    public static Developer developer = Developer.Michael;

    enum Developer {
        Michael,
        Maybe,
        Twelve
    }
}
