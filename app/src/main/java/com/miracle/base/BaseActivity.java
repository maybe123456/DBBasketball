package com.miracle.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.miracle.R;
import com.miracle.base.network.INetStatusUI;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.view.ZPlaceHolder;
import com.miracle.databinding.ActivityBaseBinding;
import com.miracle.michael.lottery.activity.LotteryMainActivity;
import com.yanzhenjie.sofia.Sofia;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity implements View.OnClickListener, INetStatusUI {

    protected Context mContext;
    protected B binding;
    protected ActivityBaseBinding mBaseBinding;
    protected ProgressDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getDecorView().setBackgroundColor(CommonUtils.getColor(R.color.main_bg_color));
        mContext = this;
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
//        binding = DataBindingUtil.inflate(getLayoutInflater(), getLayout(), null, false);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), getLayout(), null, false);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(params);
        mBaseBinding.baseContainer.addView(binding.getRoot());
        getWindow().setContentView(mBaseBinding.getRoot());

        loadingDialog = new ProgressDialog(mContext);
        loadingDialog.setMessage("加载中...");

        mBaseBinding.placeHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBaseBinding.placeHolder.getStatus() != ZPlaceHolder.STATUS_LOADING) {
                    showLoading();
                    loadData();
                }
            }
        });
        //设置状态栏颜色
//        Sofia.with(this).statusBarBackground(CommonUtils.getColor(R.color.titlebar_color)).statusBarDarkFont();
//        setStatusBarColor();
        initView();
        initListener();
        loadData();
    }

    public abstract int getLayout();

    public abstract void initView();

    public abstract void initListener();

    public abstract void loadData();

    public void showContent() {
        mBaseBinding.placeHolder.setVisibility(View.GONE);
        mBaseBinding.baseContainer.setVisibility(View.VISIBLE);
    }

    public void showPlaceHolder() {
        mBaseBinding.placeHolder.setVisibility(View.VISIBLE);
        mBaseBinding.baseContainer.setVisibility(View.GONE);
    }

    public void showLoading() {
        mBaseBinding.placeHolder.setLoading();
        showPlaceHolder();
    }

    public void showEmpty() {
        mBaseBinding.placeHolder.setEmpty();
        showPlaceHolder();
    }

    public void showError() {
        mBaseBinding.placeHolder.setError();
        showPlaceHolder();
    }

    protected void hideTitle() {
        mBaseBinding.titleBar.setVisibility(View.GONE);
    }

    protected void showRight(boolean show) {
        mBaseBinding.titleBar.showRight(show);
    }

    protected void showLeft(boolean show) {
        mBaseBinding.titleBar.showLeft(show);
    }

    protected void setTitle(String title) {
        mBaseBinding.titleBar.setTitle(title);
    }

    protected void setTitle(String title, int textColor) {
        mBaseBinding.titleBar.setTitle(title, textColor);
    }

    protected void setTitle(String title, int textColor, int textSize) {
        mBaseBinding.titleBar.setTitle(title, textColor, textSize);
    }

    protected void setRight(String text) {
        mBaseBinding.titleBar.setRight(text);
    }

    protected void setRight(String text, int textColor) {
        mBaseBinding.titleBar.setRight(text, textColor);
    }

    protected void setRight(String text, int textColor, int textSize) {
        mBaseBinding.titleBar.setRight(text, textColor, textSize);
    }


    protected void setLeft(int textColor, int textSize) {
        mBaseBinding.titleBar.setLeft(textColor, textSize);
    }

    protected void setLeft(String text, int textColor, int textSize) {
        mBaseBinding.titleBar.setLeft(text, textColor, textSize);
    }

    protected void setLeftColor(int textColor) {
        mBaseBinding.titleBar.setLeftColor(textColor);
    }

    protected void setRightClickListener(View.OnClickListener listener) {
        mBaseBinding.titleBar.setRightClickListener(listener);
    }

    protected void setLeftClickListener(View.OnClickListener listener) {
        mBaseBinding.titleBar.setLeftClickListener(listener);
    }


    private void setStatusBarColor() {
        if (mContext instanceof LotteryMainActivity) {
            Sofia.with(this).invasionStatusBar().statusBarBackgroundAlpha(0);
        } else {
            Sofia.with(this).statusBarBackground(CommonUtils.getColor(R.color.white)).statusBarDarkFont();
        }
    }

    public void showLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (null != loadingDialog) {
            loadingDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != loadingDialog) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }
    }
}
