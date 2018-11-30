package com.miracle.base.http.model;//package chidean.sanfangyuan.com.chideanapplication.http.model;
//
//import android.content.Context;
//
//
//import java.util.HashMap;
//
//import chidean.sanfangyuan.com.chideanapplication.http.HttpService;
//import chidean.sanfangyuan.com.chideanapplication.http.callback.ResultCallback;
//import chidean.sanfangyuan.com.chideanapplication.http.model.interfacepkg.IHttpModel;
//
//public class IHttpModelImpl implements IHttpModel {
//
//    @Override
//    public void userLogin(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().userLogin(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void getRegisterCode(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getRegisterCode(tag,map,callback);
//    }
//
//    @Override
//    public void changePwd(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().changePwd(tag,map,callback);
//    }
//
//    @Override
//    public void getUserInfo(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getUserInfo(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void editUser(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().editUser(tag,map,callback);
//    }
//
//    @Override
//    public void upLoadImg(String tag, String path, ResultCallback callback) {
//        HttpService.getInstance().upLoadImg(tag,path,callback);
//    }
//
//    @Override
//    public void getRegisterDepart(String tag, int page, double longitude, double latitude, ResultCallback callback) {
//        HttpService.getInstance().getRegisterDepart(tag,page,longitude,latitude,callback);
//    }
//
//    @Override
//    public void registerAccount(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().registerAccount(tag,map,callback);
//    }
//
//    @Override
//    public void getApprovalList(String tag, int page, ResultCallback callback) {
//        HttpService.getInstance().getApprovalList(tag,page,callback);
//    }
//
//    @Override
//    public void editReview(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().editReview(tag,map,callback);
//    }
//
//    @Override
//    public void getApprovalDoneList(String tag, int page, ResultCallback callback) {
//        HttpService.getInstance().getApprovalDoneList(tag,page,callback);
//    }
//
//    @Override
//    public void resetPwd(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().resetPwd(tag,map,callback);
//    }
//
//    @Override
//    public void getDepartList(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getDepartList(tag,map,callback);
//    }
//
//    @Override
//    public void addDepart(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().addDepart(tag,map,callback);
//    }
//
//    @Override
//    public void getDeviceList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getDeviceList(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void sendCrashToServer(String tag, String exception, ResultCallback callback) {
//        HttpService.getInstance().sendCrashToServer(tag,exception,callback);
//    }
//
//    @Override
//    public void getAlarmList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getAlarmList(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void getDeviceType(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getDeviceType(tag,map,callback);
//    }
//
//    @Override
//    public void addDevice(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().addDevice(tag,map,callback);
//    }
//
//    @Override
//    public void getRoomList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getRoomList(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void getDeviceTypeList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getDeviceTypeList(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void getProblemList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getProblemList(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void getProblemInfo(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getProblemInfo(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void addAdvice(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().addAdvice(tag,map,callback);
//    }
//
//    @Override
//    public void getDeviceNumber(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getDeviceNumber(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void getDeviceInfo(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getDeviceInfo(tag,cacheKey,map,callback);
//    }
//
////    @Override
////    public void getDeviceWarnList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
////        HttpService.getInstance().getDeviceWarnList(tag,cacheKey,map,callback);
////    }
//
//    @Override
//    public void getDeviceState(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getDeviceState(tag,cacheKey,map,callback);
//    }
//
//    @Override
//    public void getSearchDepart(String tag, HashMap<String, String> map, ResultCallback callback) {
//        HttpService.getInstance().getSearchDepart(tag,map,callback);
//    }
//
////    @Override
////    public void getDeviceEventList(String tag, String cacheKey, HashMap<String, String> map, ResultCallback callback) {
////        HttpService.getInstance().getDeviceEventList(tag,cacheKey,map,callback);
////    }
//}