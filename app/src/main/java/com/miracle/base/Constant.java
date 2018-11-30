package com.miracle.base;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface Constant {

    /**
     * 已选中频道的json
     */
    String SELECTED_CHANNEL_JSON = "selectedChannelJson";
    /**
     * w未选频道的json
     */
    String UNSELECTED_CHANNEL_JSON = "unselectChannelJson";
    /**
     * 频道对应的请求参数
     */
    String CHANNEL_CODE = "channelCode";
    String IS_VIDEO_LIST = "isVideoList";
    String DATA_SELECTED = "dataSelected";
    String DATA_UNSELECTED = "dataUnselected";
    String INTENT_KEY_SCAN_RESULT = "intent_key_scan_result";
    String POST_ID = "post_id";
    String MY_CIRCLE = "MyCircle";
    String USER_ID = "";
    String USER_INFO = "user_info";
    String COMMENT_LIST = "comment_list";
    String APPID = "";
    String TITLE = "title";
    String REQKEY = "reqKey";
    String CONTENT = "content";
    String testName = "18888888888";
    String testPassword = "888888";
    //QQ聊天
    String qqUrl = "mqq://im/chat?chat_type=crm&uin=%1$s&version=1&src_type=web";
    //微信聊天
    String wxUrl = "vnd.android.cursor.item/vnd.com.tencent.mm.chatting.profile";


    int QQLOGIN = 1;
    int LINKENDINLOGIN = 2;
    int WECHATLOGIN = 3;
    int REQUSET_CODE_MEDIA = 0;
    int REQUSET_CODE_CAMERA = 1;
    int REQUSET_CODE_MEFRAGMENT_ACCOUNTCENTER = 2;
    int REQUSET_CODE_SCAN = 3;
    int REQUSET_CODE_PUBLISH_POST = 4;
    int FROM_ProductDetailsAct = 0;
    int FROM_MeFragment = 1;
}
