package com.miracle.michael.chess.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZService;
import com.miracle.databinding.F2ChildChessBinding;
import com.miracle.michael.chess.activity.ChessNewsDetailActivity;
import com.miracle.michael.chess.adapter.ChessListAdapter;
import com.miracle.michael.chess.bean.ChessTypeBean;

/**
 * Created by Administrator on 2018/3/5.
 */

public class ChessF2Child extends BaseFragment<F2ChildChessBinding> {


    private ChessListAdapter mAdapter;
    private PageLoadCallback callBack;
    private ChessTypeBean footballKey;
    private int reqKey = 1;


    @Override
    public int getLayout() {
        return R.layout.f2_child_chess;
    }

    @Override
    public void initView() {
        binding.recyclerView.setAdapter(mAdapter = new ChessListAdapter(mContext));
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
                ZClient.getService(ZService.class).getChessNewsList(reqKey, page, limit).enqueue(callBack);
            }
        };
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }


    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, ChessNewsDetailActivity.class).putExtra("id", mAdapter.getItem(position).getId()));

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    public ChessF2Child setReqKey(ChessTypeBean qipaiType) {
        this.reqKey = qipaiType.getClass_id();
        this.footballKey = qipaiType;
        return this;
    }
}
