package com.miracle.base.http;

/**
 * Created by Administrator on 2017/10/24.
 */

public class HttpConstant {

    public final static String BASEURL = "http://192.168.8.28/wulian/index.php?";

    public final static String LOGIN = BASEURL+"s=/App/User/appLogin";

    public final static String REGISTER_CODE = BASEURL + "s=/App/User/sendCode";

    public final static String CHANGE_PWD = BASEURL + "s=/App/User/submitPassword";

    public final static String EDIT_USER = BASEURL + "s=/App/User/updateUserInfo";

    public final static String GET_USER_INFO = BASEURL + "s=/App/User/getUserInfo";

    public final static String UPLOAD_IMG = BASEURL + "s=/App/User/uploadImg";

    public final static String GET_REGISTER_DEPART = BASEURL + "s=/App/Department/deptList";

    public final static String REGISTER_ACCOUNT = BASEURL + "s=/App/User/appReg";

    public final static String APPROVAL_LIST = BASEURL + "s=/App/User/reviewList";

    public final static String EDIT_REVIEW = BASEURL + "s=/App/User/editReview";

    public final static String APPROVAL_DONE_LIST = BASEURL + "s=/App/User/approvedList";

    public final static String RESET_PWD = BASEURL + "s=/App/User/forgetPwd";

    public final static String GET_DEPART_LIST = BASEURL + "s=/App/department/allDept";

    public final static String ADD_DEPART = BASEURL + "s=/App/department/addDept";

    public final static String DEVICE_LIST = BASEURL + "s=/App/Device/getSomeoneDevice";

    public final static String SEND_CRASH = BASEURL + "s=/App/Apperr/uploadErr";

    public final static String WARN_LIST = BASEURL + "s=/App/Devicewarninglist/warningList";

    public final static String GET_DEVICE_TYPE = BASEURL + "s=/App/Device/getModel";

    public final static String ADD_DEVICE = BASEURL + "s=/App/Device/addDevice";

    public final static String ROOM_LIST = BASEURL + "s=/App/department/getRooms";

    public final static String DEVICE_TYPE_LIST = BASEURL + "s=/App/Help/getDeviceType";

    public final static String PROBLEM_LIST = BASEURL + "s=/App/Help/getTypeHelp";

    public final static String PROBLEM_INFO = BASEURL + "s=/App/Help/getHelpInfo";

    public final static String ADD_ADVICE = BASEURL + "s=/App/Help/addReport";

    public final static String DEVICE_NUMBER = BASEURL + "s=/App/Device/getDeviceNum";

    public final static String DEVICE_INFO = BASEURL + "s=/App/Device/deviceInfo";

    public final static String DEVICE_WARN_LIST = BASEURL + "s=/App/Devicewarninglist/oneDeviceWarning";

    public final static String DEVICE_STATE = BASEURL + "s=/App/Device/deviceTemp";

    public final static String SEARCH_DAPART_LIST = BASEURL + "s=/App/department/fuzzySearch";

    public final static String DEVICE_EVENT_LIST = BASEURL + "s=/App/Devicewarninglist/deviceLog";

}
