package com.miracle.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miracle.R;
import com.miracle.base.network.INetStatusUI;
import com.miracle.base.view.TitleBar;
import com.miracle.databinding.FragmentBaseBinding;

public abstract class BaseFragment<B extends ViewDataBinding> extends Fragment implements View.OnClickListener ,INetStatusUI {
    public Context mContext;
    public B binding;
    public FragmentBaseBinding mBaseBinding;

//    public View.OnClickListener leftListener;
    public String title = "";
    public boolean isShowTitle;
    public boolean isShowBack = true;

    public enum ShowStat{
        LOADING,
        NORMAL,
        NODATA,
        ERR
    }
    private ShowStat showStat = ShowStat.NORMAL;

    public void setUIStatus(ShowStat status){
        showStat = status;
        switch (status){
            case LOADING:
                mBaseBinding.placeHolder.setLoading();
                mBaseBinding.placeHolder.setVisibility(View.VISIBLE);
                mBaseBinding.baseFragContainer.setVisibility(View.VISIBLE);
                break;
            case NODATA:
                mBaseBinding.placeHolder.setEmpty();
                mBaseBinding.placeHolder.setVisibility(View.VISIBLE);
                mBaseBinding.baseFragContainer.setVisibility(View.VISIBLE);
                break;
            case ERR:
                mBaseBinding.placeHolder.setError();
                mBaseBinding.placeHolder.setVisibility(View.VISIBLE);
                mBaseBinding.baseFragContainer.setVisibility(View.GONE);
                break;
            case NORMAL:
                mBaseBinding.placeHolder.setVisibility(View.GONE);
                mBaseBinding.baseFragContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    public ShowStat getUIStatus() {
        return showStat;
    }

    protected void initUIStatus(){
        mBaseBinding.placeHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showStat != ShowStat.LOADING){
                    mBaseBinding.placeHolder.setLoading();
                    loadData();
                }
            }
        });
        setUIStatus(ShowStat.NORMAL);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mBaseBinding == null) {
            mBaseBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_base, null, false);
//            binding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), getLayout(), null, false);
            binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), getLayout(), null, false);
            mBaseBinding.baseFragContainer.addView(binding.getRoot());

            initTitleBar();
            initUIStatus();

            initView();
            initListener();
        }

        return mBaseBinding.getRoot();
    }


    public abstract int getLayout();

    public abstract void initView();

    public abstract void initListener();

    public void showError(){
        setUIStatus(ShowStat.ERR);
    }
    public void showContent(){
        setUIStatus(ShowStat.NORMAL);
    }
    public void showEmpty(){
        setUIStatus(ShowStat.NODATA);
    }
    public void showLoading(){
        setUIStatus(ShowStat.LOADING);
    }

    public void loadData(){

    }

    public void initTitleBar(){
//        setLeftClickListener(leftListener);
        if(!TextUtils.isEmpty(title))
            setTitle(title);
        setShowTitle(isShowTitle);
        setShowTitleBack(isShowBack);
    }

//    public void setLeftClickListener(View.OnClickListener listener) {
//        leftListener = listener;
//        if(mBaseBinding != null && mBaseBinding.titlebar != null && leftListener != null)
//            mBaseBinding.titlebar.setLeftClickListener(listener);
//    }

    public void setShowTitle(boolean isShowTitle){
        this.isShowTitle = isShowTitle;
        if(mBaseBinding != null && mBaseBinding.titlebarFrag != null){
            if(isShowTitle)
                mBaseBinding.getRoot().findViewById(R.id.titlebar_frag).setVisibility(View.VISIBLE);
            else
                mBaseBinding.getRoot().findViewById(R.id.titlebar_frag).setVisibility(View.GONE);
        }
    }

    public void showTitle(){
        setShowTitle(true);
    }

    public void setTitle(String title){
        this.title = title;
        if(mBaseBinding != null) {
            ((TitleBar) mBaseBinding.getRoot().findViewById(R.id.titlebar_frag)).setTitle(title);
        }
    }

    public TitleBar getTitleBar(){
        return ((TitleBar)mBaseBinding.getRoot().findViewById(R.id.titlebar_frag));
    }

    public void setShowTitleBack(boolean isShow){
        isShowBack = isShow;
        if(mBaseBinding != null)
            ((TitleBar)mBaseBinding.getRoot().findViewById(R.id.titlebar_frag)).showLeft(isShow);
    }
}
