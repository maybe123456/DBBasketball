package com.miracle.michael.lottery.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZService;
import com.miracle.databinding.F3ChildLotteryBinding;
import com.miracle.michael.lottery.adapter.LotteryResultListAdapter;
import com.miracle.michael.lottery.bean.LotteryBean;

/**
 * Created by Administrator on 2018/3/5.
 */

public class LotteryF3Child extends BaseFragment<F3ChildLotteryBinding> {


    private LotteryResultListAdapter mAdapter;
    private PageLoadCallback callBack;
    private LotteryBean footballKey;
    private int reqKey = 1;


    @Override
    public int getLayout() {
        return R.layout.f3_child_lottery;
    }

    @Override
    public void initView() {
        binding.recyclerView.setAdapter(mAdapter = new LotteryResultListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        binding.recyclerView.setLayoutAnimation(animation);
        initCallback();
    }

    private void initCallback() {
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(ZService.class).getLotteryResultList(reqKey, page, limit).enqueue(callBack);
            }
        };
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }


    @Override
    public void initListener() {
    }


    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    public LotteryF3Child setReqKey2(LotteryBean footballKey) {
        this.reqKey = footballKey.getId();
        this.footballKey = footballKey;
        return this;
    }
}
