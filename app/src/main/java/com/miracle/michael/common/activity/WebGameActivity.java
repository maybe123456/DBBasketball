package com.miracle.michael.common.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebGameActivity extends Activity {

    private WebView webView;

    private String mUrl = "http://m.7724.com/mdh5/game";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        WebSettings s = webView.getSettings();
        s.setBuiltInZoomControls(false);
        s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        s.setUseWideViewPort(true);
        s.setLoadWithOverviewMode(true);
        s.setSavePassword(true);
        s.setSaveFormData(true);
        s.setJavaScriptEnabled(true);
        s.setGeolocationEnabled(true);
        s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        s.setDomStorageEnabled(true);

        s.setAllowContentAccess(true);
        s.setAllowFileAccess(true);
        s.setAllowFileAccessFromFileURLs(true);
        s.setAllowUniversalAccessFromFileURLs(true);
        s.setAppCacheEnabled(true);
//        s.setAppCachePath(context.getCacheDir().toString());
        s.setDatabaseEnabled(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);

        webView.requestFocus();
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        setContentView(webView);
        webView.loadUrl(mUrl);

//        fuck();
    }

//    private void fuck() {
//
//        final TextView tv = new TextView(this);
//        tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "fuck", Toast.LENGTH_LONG).show();
//                OkGo.<String>get("http://9.988lhkj.com/article/zc/3/10").execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        tv.setText(response.body());
//                    }
//                });
//            }
//        });
//        tv.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
//        setContentView(tv);
//
//    }

}
