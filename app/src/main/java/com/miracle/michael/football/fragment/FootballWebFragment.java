package com.miracle.michael.football.fragment;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.databinding.FragmentFootballWebBinding;
import com.miracle.michael.football.bean.FootballRankDetailBean;


public class FootballWebFragment extends BaseFragment<FragmentFootballWebBinding> {
    private String type;


    @Override
    public int getLayout() {
        return R.layout.fragment_football_web;
    }

    @Override
    public void initView() {
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


        binding.webView.setHorizontalScrollBarEnabled(false);//水平不显示
        binding.webView.setVerticalScrollBarEnabled(false); //垂直不显示
        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
//        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

//        setContentView(webView);
//        binding.webView.loadUrl(mUrl);
        reqData(type);
    }

    private void reqData(String type) {
        ZClient.getService(ZService.class).getFootballRankDetail(type).enqueue(new ZCallback<ZResponse<FootballRankDetailBean>>() {
            @Override
            public void onSuccess(ZResponse<FootballRankDetailBean> data) {
                binding.webView.loadDataWithBaseURL(null, CommonUtils.getHtmlData(data.getData().getContent()), "text/html", "utf-8", null);

            }
        });
    }

    public FootballWebFragment setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

}
