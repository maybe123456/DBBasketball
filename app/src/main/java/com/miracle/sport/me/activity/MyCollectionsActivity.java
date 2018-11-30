package com.miracle.sport.me.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.SwipeRecyclerBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.home.activity.SimpleWebCommentActivity;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.me.adapter.CollectionsListAdapter;

import java.util.List;

public class MyCollectionsActivity extends BaseActivity<SwipeRecyclerBinding> {

    private CollectionsListAdapter mAdapter;
    private ZPageLoadCallback callBack;

    @Override
    public int getLayout() {
        return R.layout.swipe_recycler;
    }

    @Override
    public void initView() {
        setTitle("我的收藏");
        binding.recyclerView.setAdapter(mAdapter = new CollectionsListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        showLoadingDialog();
        initCallback();
    }

    private void initCallback() {
        callBack = new ZPageLoadCallback<ZResponse<List<Football>>>(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                callBack.setCachKey("LotteryMyCollections"+page);
//                ZClient.getService(SportService.class).getMycollections(page,limit).enqueue(callBack);
                RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getMycollections(page,limit),callBack);

            }
        };

        callBack.setDialog(loadingDialog);
        callBack.setNetStatusUI(this);
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, SimpleWebCommentActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
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
