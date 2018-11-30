package com.miracle.michael.football.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZService;
import com.miracle.databinding.SwipeRecyclerBinding;
import com.miracle.michael.football.adapter.FootballListAdapter;

public class FootballSaiShiFenXiActivity extends BaseActivity<SwipeRecyclerBinding> {

    private FootballListAdapter mAdapter;
    private PageLoadCallback callBack;

    @Override
    public int getLayout() {
        return R.layout.swipe_recycler;
    }

    @Override
    public void initView() {
        setTitle("赛事分析");
        binding.recyclerView.setAdapter(mAdapter = new FootballListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initCallback();
    }

    private void initCallback() {
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(ZService.class).getFootballNewsList("zqbfyc", page, limit).enqueue(callBack);
            }
        };
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }


    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, FootballNewsDetailActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {

    }
}
