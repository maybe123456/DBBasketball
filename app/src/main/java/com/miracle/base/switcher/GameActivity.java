package com.miracle.base.switcher;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.ActivityGameBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Administrator on 2018/5/10.
 */

public class GameActivity extends BaseActivity<ActivityGameBinding> {
    /**
     * 拍照/选择文件请求码
     */
    private static final int REQ_CAMERA = 1;
    private static final int REQ_CHOOSE = REQ_CAMERA + 1;
    public static File tempFile;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageArray;
    private boolean isError;
    private String mUrl;
    private long mExitTime;

    @Override
    public int getLayout() {
        return R.layout.activity_game;
    }

    @Override
    public void initView() {
        hideTitle();
        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        binding.webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        binding.webView.setWebViewClient(new MyWebViewClient());
        binding.webView.setWebChromeClient(new MyChromeClient());
        binding.webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        mUrl = getIntent().getStringExtra("url");

    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {
        isError = false;
        binding.webView.loadUrl(mUrl);
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack();
                return true;
            } else if (System.currentTimeMillis() - mExitTime > 2000) {
                mExitTime = System.currentTimeMillis();
                ToastUtil.toast("再按一次退出");
                return true;
            }
        }
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

    @Override
    public void onClick(View v) {

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
                if (isError) {
                    showError();
                } else {
                    showContent();
                }
            }
        }
    }


    private final class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
            isError = true;
            showError();
        }

        // 新版本，只会在Android6及以上调用
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (request.isForMainFrame()) { // 或者： if(request.getUrl().toString() .equals(getUrl()))
                // 在这里显示自定义错误页
                isError = true;
                showError();
            }
        }
    }

}
