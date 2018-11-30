package com.miracle.base.http.model.interfacepkg;


import com.miracle.base.http.callback.ResultCallback;

import java.util.HashMap;


/**
 * Created by Dengbocheng on 2017/5/24.
 */

public interface IHttpModel {

    /**
     * 用户登录
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void userLogin(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取注册验证码
     * @param tag
     * @param map
     * @param callback
     */
    void getRegisterCode(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 个人中心修改密码
     * @param tag
     * @param map
     * @param callback
     */
    void changePwd(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取用户信息
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getUserInfo(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 编辑用户信息
     * @param tag
     * @param map
     * @param callback
     */
    void editUser(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 上传图片
     * @param tag
     * @param path
     * @param callback
     */
    void upLoadImg(String tag, String path, ResultCallback callback);

    /**
     * 获取注册单位列表
     * @param tag
     * @param page
     * @param longitude
     * @param latitude
     * @param callback
     */
    void getRegisterDepart(String tag, int page, double longitude, double latitude, ResultCallback callback);

    /**
     * 注册账号
     * @param tag
     * @param map
     * @param callback
     */
    void registerAccount(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取未审批列表
     * @param tag
     * @param page
     * @param callback
     */
    void getApprovalList(String tag, int page, ResultCallback callback);

    /**
     * 申请审核
     * @param tag
     * @param map
     * @param callback
     */
    void editReview(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取已审批列表
     * @param tag
     * @param page
     * @param callback
     */
    void getApprovalDoneList(String tag, int page, ResultCallback callback);

    /**
     * 忘记密码
     * @param tag
     * @param map
     * @param callback
     */
    void resetPwd(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取部门科室
     * @param tag
     * @param map
     * @param callback
     */
    void getDepartList(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 添加部门
     * @param tag
     * @param map
     * @param callback
     */
    void addDepart(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取设备列表
     * @param tag
     * @param map
     * @param callback
     */
    void getDeviceList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 上传错误日志到服务器
     * @param tag
     * @param exception
     * @param callback
     */
    void sendCrashToServer(String tag, String exception, ResultCallback callback);

    /**
     * 获取报警列表
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getAlarmList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取设备名和型号
     * @param tag
     * @param map
     * @param callback
     */
    void getDeviceType(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 添加设备
     * @param tag
     * @param map
     * @param callback
     */
    void addDevice(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取房间号列表
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getRoomList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取设备类型列表
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getDeviceTypeList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取常见问题列表
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getProblemList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取常见问题详情
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getProblemInfo(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 意见反馈
     * @param tag
     * @param map
     * @param callback
     */
    void addAdvice(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取设备总数量
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getDeviceNumber(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取设备信息
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getDeviceInfo(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取报警记录列表
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
//    void getDeviceWarnList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取设备状态信息
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
    void getDeviceState(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取注册搜索部门列表
     * @param tag
     * @param map
     * @param callback
     */
    void getSearchDepart(String tag, HashMap<String, String> map, ResultCallback callback);

    /**
     * 获取事件记录列表
     * @param tag
     * @param cacheKey
     * @param map
     * @param callback
     */
//    void getDeviceEventList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback);

}
