package com.miracle.sport.me.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.databinding.SwipeRecyclerBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.activity.PostDetailActivity;
import com.miracle.sport.community.adapter.PostListAdapter;

public class DDZMyPostActivity extends BaseActivity<SwipeRecyclerBinding> {

    private PostListAdapter mAdapter;
    private ZPageLoadCallback callBack;

    @Override
    public int getLayout() {
        return R.layout.swipe_recycler;
    }

    @Override
    public void initView() {
        setTitle("我的发帖");
        binding.recyclerView.setAdapter(mAdapter = new PostListAdapter(this));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initCallback();
    }

    private void initCallback() {
        callBack = new ZPageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(SportService.class).getMyPostList(page, limit).enqueue(callBack);
            }
        };
        callBack.setNetStatusUI(this);
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, PostDetailActivity.class).putExtra(Constant.POST_ID, mAdapter.getItem(position).getId()));
            }
        });
    }

    @Override
    public void loadData() {
        mAdapter.setNewData(null);
        callBack.onRefresh();
    }

    @Override
    public void onClick(View v) {

    }
}
