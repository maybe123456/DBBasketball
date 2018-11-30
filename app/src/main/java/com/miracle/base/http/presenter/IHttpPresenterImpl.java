package com.miracle.base.http.presenter;//package chidean.sanfangyuan.com.chideanapplication.http.presenter;
//
//import android.content.Context;
//
//import java.util.HashMap;
//
//import chidean.sanfangyuan.com.chideanapplication.http.callback.ResultCallback;
//import chidean.sanfangyuan.com.chideanapplication.http.model.IHttpModelImpl;
//import chidean.sanfangyuan.com.chideanapplication.http.model.interfacepkg.IHttpModel;
//import chidean.sanfangyuan.com.chideanapplication.http.presenter.interfacepkg.IHttpPresenter;
//import chidean.sanfangyuan.com.chideanapplication.http.view.IBaseView;
//
//public class IHttpPresenterImpl implements IHttpPresenter {
//    private IBaseView view;
//    private IHttpModel serverModel;
//    public IHttpPresenterImpl(IBaseView view) {
//        this.view = view;
//        this.serverModel = new IHttpModelImpl();
//    }
//
//    @Override
//    public void userLogin(String tag, String cacheKey, HashMap<String,String> map) {
//        serverModel.userLogin(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                view.onCacheSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getRegisterCode(String tag, HashMap<String, String> map) {
//        serverModel.getRegisterCode(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void changePwd(String tag, HashMap<String, String> map) {
//        serverModel.changePwd(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getUserInfo(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getUserInfo(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                view.onCacheSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void editUser(String tag, HashMap<String, String> map) {
//        serverModel.editUser(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void upLoadImg(String tag, String path) {
//        serverModel.upLoadImg(tag, path, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getRegisterDepart(String tag, int page, double longitude, double latitude) {
//        serverModel.getRegisterDepart(tag, page,longitude,latitude, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void registerAccount(String tag, HashMap<String, String> map) {
//        serverModel.registerAccount(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getApprovalList(String tag, int page) {
//        serverModel.getApprovalList(tag, page, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void editReview(String tag, HashMap<String, String> map) {
//        serverModel.editReview(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getApprovalDoneList(String tag, int page) {
//        serverModel.getApprovalDoneList(tag, page, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void resetPwd(String tag, HashMap<String, String> map) {
//        serverModel.resetPwd(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getDepartList(String tag, HashMap<String, String> map) {
//        serverModel.getDepartList(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void addDepart(String tag, HashMap<String, String> map) {
//        serverModel.addDepart(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getDeviceList(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getDeviceList(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                super.onCacheSuccess(tag, data);
//                view.onCacheSuccess(tag, data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void sendCrashToServer(String tag, String exception) {
//        serverModel.sendCrashToServer(tag, exception, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getAlarmList(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getAlarmList(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                super.onCacheSuccess(tag, data);
//                view.onCacheSuccess(tag, data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getDeviceType(String tag, HashMap<String, String> map) {
//        serverModel.getDeviceType(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void addDevice(String tag, HashMap<String, String> map) {
//        serverModel.addDevice(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getRoomList(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getRoomList(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                super.onCacheSuccess(tag, data);
//                view.onCacheSuccess(tag, data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getDeviceTypeList(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getDeviceTypeList(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                super.onCacheSuccess(tag, data);
//                view.onCacheSuccess(tag, data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getProblemList(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getProblemList(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                super.onCacheSuccess(tag, data);
//                view.onCacheSuccess(tag, data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getProblemInfo(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getProblemInfo(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                super.onCacheSuccess(tag, data);
//                view.onCacheSuccess(tag, data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void addAdvice(String tag, HashMap<String, String> map) {
//        serverModel.addAdvice(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getDeviceNumber(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getDeviceNumber(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                super.onCacheSuccess(tag, data);
//                view.onCacheSuccess(tag, data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
//    @Override
//    public void getDeviceInfo(String tag, String cacheKey, HashMap<String, String> map) {
//        serverModel.getDeviceInfo(tag, cacheKey, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onCacheSuccess(String tag, Object data) {
//                super.onCacheSuccess(tag, data);
//                view.onCacheSuccess(tag, data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
////    @Override
////    public void getDeviceWarnList(String tag, String cacheKey, HashMap<String, String> map) {
////        serverModel.getDeviceWarnList(tag, cacheKey, map, new ResultCallback() {
////            @Override
////            public void onSuccess(String tag, Object data) {
////                super.onSuccess(tag, data);
////                view.onSuccess(tag,data);
////            }
////
////            @Override
////            public void onCacheSuccess(String tag, Object data) {
////                super.onCacheSuccess(tag, data);
////                view.onCacheSuccess(tag, data);
////            }
////
////            @Override
////            public void onFailure(String tag, String exception) {
////                super.onFailure(tag, exception);
////                view.onFail(tag,exception);
////            }
////        });
////    }
//
////    @Override
////    public void getDeviceState(String tag, String cacheKey, HashMap<String, String> map) {
////        serverModel.getDeviceState(tag, cacheKey, map, new ResultCallback() {
////            @Override
////            public void onSuccess(String tag, Object data) {
////                super.onSuccess(tag, data);
////                view.onSuccess(tag,data);
////            }
////
////            @Override
////            public void onCacheSuccess(String tag, Object data) {
////                super.onCacheSuccess(tag, data);
////                view.onCacheSuccess(tag, data);
////            }
////
////            @Override
////            public void onFailure(String tag, String exception) {
////                super.onFailure(tag, exception);
////                view.onFail(tag,exception);
////            }
////        });
////    }
//
//    @Override
//    public void getSearchDepart(String tag, HashMap<String, String> map) {
//        serverModel.getSearchDepart(tag, map, new ResultCallback() {
//            @Override
//            public void onSuccess(String tag, Object data) {
//                super.onSuccess(tag, data);
//                view.onSuccess(tag,data);
//            }
//
//            @Override
//            public void onFailure(String tag, String exception) {
//                super.onFailure(tag, exception);
//                view.onFail(tag,exception);
//            }
//        });
//    }
//
////    @Override
////    public void getDeviceEventList(String tag, String cacheKey, HashMap<String, String> map) {
////
////    }
//
////    @Override
////    public void getDeviceEventList(String tag, String cacheKey, HashMap<String, String> map) {
////        serverModel.getDeviceEventList(tag, cacheKey, map, new ResultCallback() {
////            @Override
////            public void onSuccess(String tag, Object data) {
////                super.onSuccess(tag, data);
////                view.onSuccess(tag,data);
////            }
////
////            @Override
////            public void onCacheSuccess(String tag, Object data) {
////                super.onCacheSuccess(tag, data);
////                view.onCacheSuccess(tag, data);
////            }
////
////            @Override
////            public void onFailure(String tag, String exception) {
////                super.onFailure(tag, exception);
////                view.onFail(tag,exception);
////            }
////        });
////    }
//
//}
