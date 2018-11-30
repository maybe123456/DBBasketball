package com.miracle.michael.lottery.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZService;
import com.miracle.databinding.F2ChildLotteryBinding;
import com.miracle.michael.lottery.adapter.LotteryResultListAdapter;
import com.miracle.michael.lottery.bean.LotteryBean;

/**
 * Created by Administrator on 2018/3/5.
 */

public class LotteryF2Child extends BaseFragment<F2ChildLotteryBinding> {


    private LotteryResultListAdapter mAdapter;
    private PageLoadCallback callBack;
    private LotteryBean footballKey;
    private int reqKey = 1;


    @Override
    public int getLayout() {
        return R.layout.f2_child_lottery;
    }

    @Override
    public void initView() {
        binding.recyclerView.setAdapter(mAdapter = new LotteryResultListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        binding.tvCategoryTitle.setText(footballKey == null ? "双色球" : footballKey.getName());

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
//        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                startActivity(new Intent(mContext, SimpleWebActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
//            }
//        });
    }


    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }

    @Override
    public void onClick(View view) {

    }

    public void setReqKey(LotteryBean footballKey) {
        this.reqKey = footballKey.getId();
        binding.tvCategoryTitle.setText(footballKey.getName());
        callBack.onRefresh();
    }

    public LotteryF2Child setReqKey2(LotteryBean footballKey) {
        this.reqKey = footballKey.getId();
        this.footballKey = footballKey;
        return this;
    }
}
