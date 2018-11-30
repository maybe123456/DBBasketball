package com.miracle.sport.me.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.CommonUtils;
import com.miracle.databinding.SwipeRecyclerBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.activity.CircleActivity;
import com.miracle.sport.community.activity.CommunityActivity;
import com.miracle.sport.community.bean.MyCircleBean;
import com.miracle.sport.me.adapter.MyCircleAdapter;

import java.util.List;

import retrofit2.Call;

public class DDZMyCircleActivity extends BaseActivity<SwipeRecyclerBinding> {

    private MyCircleAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.swipe_recycler;
    }

    @Override
    public void initView() {
        setTitle("我的社区");
        setRight(CommonUtils.getString(R.string.icon_add), CommonUtils.getColor(R.color.white));
        setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CircleActivity.class));
            }
        });
        binding.recyclerView.setAdapter(mAdapter = new MyCircleAdapter());
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


    @Override
    public void initListener() {
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, CommunityActivity.class).putExtra(Constant.MY_CIRCLE, mAdapter.getItem(position)));
            }
        });

    }

    @Override
    public void loadData() {
        ZClient.getService(SportService.class).getMyCircleList().enqueue(new ZCallback<ZResponse<List<MyCircleBean>>>(binding.swipeRefreshLayout, this) {
            @Override
            public void onSuccess(ZResponse<List<MyCircleBean>> data) {
                mAdapter.setNewData(data.getData());
            }

            @Override
            protected void onFinish(Call<ZResponse<List<MyCircleBean>>> call) {
                super.onFinish(call);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
