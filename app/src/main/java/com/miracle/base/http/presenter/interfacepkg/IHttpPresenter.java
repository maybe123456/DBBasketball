package com.miracle.base.http.presenter.interfacepkg;


import java.util.HashMap;

/**
 * Created by Dengbocheng on 2017/5/24.
 */

public interface IHttpPresenter {

    /**
     * 用户登录
     *
     * @param cacheKey
     * @param tag
     * @param map
     */

    void userLogin(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取注册验证码
     *
     * @param tag
     * @param map
     */
    void getRegisterCode(String tag, HashMap<String, String> map);

    /**
     * 个人中心修改密码
     *
     * @param tag
     * @param map
     */
    void changePwd(String tag, HashMap<String, String> map);

    /**
     * 获取用户信息
     *
     * @param tag
     * @param map
     */
    void getUserInfo(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 编辑用户信息
     *
     * @param tag
     * @param map
     */
    void editUser(String tag, HashMap<String, String> map);

    /**
     * 上传图片
     * @param tag
     * @param path
     */
    void upLoadImg(String tag, String path);

    /**
     * 获取注册单位列表
     * @param tag
     * @param page
     * @param longitude
     * @param latitude
     */
    void getRegisterDepart(String tag, int page, double longitude, double latitude);

    /**
     * 注册账号
     * @param tag
     * @param map
     */
    void registerAccount(String tag, HashMap<String, String> map);

    /**
     * 获取未审批列表
     * @param tag
     * @param page
     */
    void getApprovalList(String tag, int page);

    /**
     * 申请审核
     * @param tag
     * @param map
     */
    void editReview(String tag, HashMap<String, String> map);

    /**
     * 获取已审批列表
     * @param tag
     * @param page
     */
    void getApprovalDoneList(String tag, int page);

    /**
     * 忘记密码
     * @param tag
     * @param map
     */
    void resetPwd(String tag, HashMap<String, String> map);

    /**
     * 获取部门科室
     * @param tag
     * @param map
     */
    void getDepartList(String tag, HashMap<String, String> map);

    /**
     * 添加部门
     * @param tag
     * @param map
     */
    void addDepart(String tag, HashMap<String, String> map);

    /**
     * 获取设备列表
     * @param tag
     * @param map
     */
    void getDeviceList(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 上传错误日志到服务器
     * @param tag
     * @param exception
     */
    void sendCrashToServer(String tag, String exception);

    /**
     * 获取报警列表
     * @param tag
     * @param cacheKey
     * @param map
     */
    void getAlarmList(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取设备名和型号
     * @param tag
     * @param map
     */
    void getDeviceType(String tag, HashMap<String, String> map);

    /**
     * 添加设备
     * @param tag
     * @param map
     */
    void addDevice(String tag, HashMap<String, String> map);

    /**
     * 获取房间号列表
     * @param tag
     * @param cacheKey
     * @param map
     */
    void getRoomList(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取设备类型列表
     * @param tag
     * @param cacheKey
     * @param map
     */
    void getDeviceTypeList(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取常见问题列表
     * @param tag
     * @param cacheKey
     * @param map
     */
    void getProblemList(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取常见问题详情
     * @param tag
     * @param cacheKey
     * @param map
     */
    void getProblemInfo(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 意见反馈
     * @param tag
     * @param map
     */
    void addAdvice(String tag, HashMap<String, String> map);

    /**
     * 获取设备总数量
     * @param tag
     * @param cacheKey
     * @param map
     */
    void getDeviceNumber(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取设备信息
     * @param tag
     * @param cacheKey
     * @param map
     */
    void getDeviceInfo(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取报警记录列表
     * @param tag
     * @param cacheKey
     * @param map
     */
//    void getDeviceWarnList(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取设备状态信息
     * @param tag
     * @param cacheKey
     * @param map
     */
//    void getDeviceState(String tag, String cacheKey, HashMap<String, String> map);

    /**
     * 获取注册搜索部门列表
     * @param tag
     * @param map
     */
    void getSearchDepart(String tag, HashMap<String, String> map);

    /**
     * 获取事件记录列表
     * @param tag
     * @param cacheKey
     * @param map
     */
//    void getDeviceEventList(String tag, String cacheKey, HashMap<String, String> map);

}
