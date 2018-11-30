package com.miracle.base.http.interceptor;//package com.exampleph.administrator.news.http.interceptor;
//
//
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//
//import com.google.gson.reflect.TypeToken;
//import com.lzy.okgo.OkGo;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.HashMap;
//
//import chidean.sanfangyuan.com.chideanbase.activity.AppBase;
//import chidean.sanfangyuan.com.chideanbase.utils.GsonUtils;
//import chidean.sanfangyuan.com.chideanbase.utils.LogUtils;
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okio.Buffer;
//import okio.BufferedSource;
//
///**
// * 全局自动刷新Token的拦截器
// */
//public class TokenInterceptor implements Interceptor {
//
//    private Handler handler = new InnerHandler();
//
//    private static class InnerHandler extends Handler {
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                Response response = (Response) msg.obj;
//                //对response需要自己做解析
//                String data = response.body().string();
////                ToastUtils.showToast(data + "dsdf");
//
//                ResultForJob<LoginEntity> loginEntity = GsonUtils.parseTObject(data, null,
//                        new TypeToken<ResultForJob<LoginEntity>>() {
//                        }.getType());
////                String ctoken = SharePreferenceManager.getToken(AppBase.getApp);
//                String ctoken = loginEntity.getData().getCtoken();
//                SharePreferenceManager.putToken(AppBase.getApp, ctoken);
////                System.out.println("同步请求的数据：" + data);
////                Toast.makeText(AppBase.getApp, "同步请求成功" + data, Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        Request request = chain.request();
//        Response response = chain.proceed(request);
//        LogUtils.info("response.code=" + response.code());
//
//        if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
////            LogUtils.info("静默自动刷新Token,然后重新请求数据");
//            //同步请求方式，获取最新的Token
////            getNewToken();
//            postLogin();
//            //使用新的Token，创建新的请求
//            String ctoken = SharePreferenceManager.getToken(AppBase.getApp);
//            Request newRequest = chain.request()
//                    .newBuilder()
//                    .header("ctoken", ctoken)
//                    .build();
//            //重新请求
//            return chain.proceed(newRequest);
//        }
//        return response;
//    }
//
//    /**
//     * 根据Response，判断Token是否失效
//     *
//     * @param response
//     * @return
//     */
////    private boolean isTokenExpired(Response response) {
////        if (response.code() == 401) {
////            return true;
////        }
////        return false;
////    }
//    private boolean isTokenExpired(Response response) {
//        if (response.code() == 200) {
//            try {
////                String body = response.body().string();
////                BaseEntity string = GsonUtils.parseTObject(body, null,
////                        new TypeToken<BaseEntity>() {
////                        }.getType());
//                ResponseBody responseBody = response.body();
//                BufferedSource source = responseBody.source();
//                source.request(Long.MAX_VALUE); // request the entire body.
//                Buffer buffer = source.buffer();
//// clone buffer before reading from it
//                String responseBodyString = buffer.clone().readString(Charset.forName("UTF-8"));
//                Log.d("TAG", responseBodyString);
//
//                BaseEntity string = GsonUtils.parseTObject(responseBodyString, null,
//                        new TypeToken<BaseEntity>() {
//                        }.getType());
//
//                if ("401".equals(string.getStatus())) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 同步请求方式，获取最新的Token
//     *
//     * @return
//     */
//    private void getNewToken() {
//
//        // 通过一个特定的接口获取新的token，此处要用到同步的retrofit请求
////        Response_Login loginInfo = CacheManager.restoreLoginInfo(BaseApplication.getContext());
////        String username = loginInfo.getUserName();
////        String password = loginInfo.getPassword();
////
////        LogUtil.print("loginInfo=" + loginInfo.toString());
////        Call<Response_Login> call = WebHelper.getSyncInterface().synclogin(new Request_Login(username, password));
////        loginInfo = call.execute().body();
////        LogUtil.print("loginInfo=" + loginInfo.toString());
////
////        loginInfo.setPassword(password);
////        CacheManager.saveLoginInfo(loginInfo);
////        return loginInfo.getSession();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HashMap<String, String> paramsNext = new HashMap<>();
//                paramsNext.put("pwd", SharePreferenceManager.getPassword(AppBase.getApp));
//                paramsNext.put("userName", SharePreferenceManager.getUserName(AppBase.getApp));
//                paramsNext.put("clientType", "ANDROID");
//                try {
//                    Response response = OkGo.post(UrlConstants.USER_CDA_LOGIN)//
//                            .tag(this)//
//                            .params(paramsNext, true)//
//                            .execute();  //不传callback即为同步请求
//
//                    Message message = Message.obtain();
//                    message.obj = response;
//                    handler.sendMessage(message);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }).start();
//
//
////        Response response = OkGo.<ResultForJob<LoginEntity>>post(UrlConstants.USER_CDA_LOGIN)//
////                .tag(this)//
////                .params(paramsNext, true)
////                .execute();
////        //对response需要自己做解析
////        String data = response.body().string();
////        ToastUtils.showToast(data+"");
////
////        LoginEntity loginEntity = GsonUtils.parseTObject(data, null,
////                new TypeToken<LoginEntity>() {
////                }.getType());
////
////        return loginEntity.getCtoken();
//    }
//
//
//    /**
//     * 登录接口
//     */
//    private void postLogin() throws IOException {
//
//        HashMap<String, String> paramsNext = new HashMap<>();
//        paramsNext.put("pwd", SharePreferenceManager.getPassword(AppBase.getApp));
//        paramsNext.put("userName", SharePreferenceManager.getUserName(AppBase.getApp));
//        paramsNext.put("clientType", "ANDROID");
//
////        Response response = OkGo.get(UrlConstants.USER_CDA_LOGIN)//
////                .tag(this)//
////                .headers("header1", "headerValue1")//
////                .params("param1", "paramValue1")//
////                .execute();  //不传callback即为同步请求
//
//        Response response = OkGo.post(UrlConstants.USER_CDA_LOGIN)//
//                .tag(this)//
//                .params(paramsNext, true)
//                .execute();
//
//        ResponseBody responseBody = response.body();
//        BufferedSource source = responseBody.source();
//        source.request(Long.MAX_VALUE); // request the entire body.
//        Buffer buffer = source.buffer();
//// clone buffer before reading from it
//        String responseBodyString = buffer.clone().readString(Charset.forName("UTF-8"));
//        Log.d("TAG", responseBodyString);
//
////        String data = response.body().string();
//
//        ResultForJob<LoginEntity> loginEntity = GsonUtils.parseTObject(responseBodyString, null,
//                new TypeToken<ResultForJob<LoginEntity>>() {
//                }.getType());
////                String ctoken = SharePreferenceManager.getToken(AppBase.getApp);
//        if (null != loginEntity.getData()) {
//            String ctoken = loginEntity.getData().getCtoken();
//            SharePreferenceManager.putToken(AppBase.getApp, ctoken);
//        }
//
//    }
//
//}
