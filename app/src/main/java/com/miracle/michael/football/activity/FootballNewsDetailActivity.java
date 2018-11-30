package com.miracle.michael.football.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.BaseActivity;
import com.miracle.base.GOTO;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.ActivitySimpleWebBinding;
import com.miracle.michael.football.bean.FootballNewsDetailBean;

public class FootballNewsDetailActivity extends BaseActivity<ActivitySimpleWebBinding> {

    private int id;


    @Override
    public int getLayout() {
        return R.layout.activity_simple_web;
    }

    @Override
    public void initView() {
        hideTitle();
        binding.tvTitle.setText("详情");
        id = getIntent().getIntExtra("id", 0);
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
        settings.setLoadWithOverviewMode(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reqData();
    }

    private void reqData() {
        ZClient.getService(ZService.class).getFootballNewsDetail(id).enqueue(new ZCallback<ZResponse<FootballNewsDetailBean>>(binding.swipeRefreshLayout) {
            @Override
            public void onSuccess(ZResponse<FootballNewsDetailBean> data) {
                binding.cbRight.setChecked(data.getData().getCoin() == 1);
                binding.webView.loadDataWithBaseURL(null, CommonUtils.getHtmlData(data.getData().getContent()), "text/html", "utf-8", null);
            }
        });
    }


    @Override
    public void initListener() {
        binding.rlLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.cbRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    ZClient.getService(ZService.class).likeOrDislike(AppConfig.APP_TYPE, id).enqueue(likeCallback);
                }
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reqData();
            }
        });
    }

    @Override
    public void loadData() {

    }

    private ZCallback<ZResponse> likeCallback = new ZCallback<ZResponse>() {
        @Override
        public void onSuccess(ZResponse data) {
            ToastUtil.toast(data.getMessage());
        }
    };

    @Override
    public void onClick(View v) {

    }

}
