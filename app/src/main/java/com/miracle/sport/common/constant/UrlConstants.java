package com.miracle.sport.common.constant;

/**********************************************************
 * @文件名称：UrlConstants.java
 **********************************************************/
public class UrlConstants {

    public static final String qqUrl = "mqq://im/chat?chat_type=wpa&uin=%1$s&version=1&src_type=web";//qq聊天
    public final static String wxUrl = "vnd.android.cursor.item/vnd.com.tencent.mm.chatting.profile";//微信聊天
    public static final int REQUESTCODE = 1001;
//    public static final String baseUrl ="http://8.988lhkj.com/" ;
    public static final String baseUrl ="http://xiaozhuang.988lhkj.com/" ;



     public static final String POST_SPORT_TYPE=baseUrl+"home/sport/type";
     public static final String POST_SPORT_LIST=baseUrl+"home/sport/sportlist";

     /**彩票类型*/
     public static final String POST_GOOD_CAIPIAO=baseUrl+"home/Goodcaipiao/type";


}
