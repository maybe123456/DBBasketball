package com.miracle.sport.community.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.RecyclerBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.activity.CircleActivity;
import com.miracle.sport.community.adapter.CircleChildAdapter;
import com.miracle.sport.community.bean.CircleBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class CircleFragment extends BaseFragment<RecyclerBinding> {

    private CircleChildAdapter mAdapter;
    private boolean isFromPublishPostActivity;


    @Override
    public int getLayout() {
        return R.layout.recycler;
    }

    @Override
    public void initView() {
        binding.recyclerView.setAdapter(mAdapter = new CircleChildAdapter());
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.tvCheck) {
                    CircleBean.ChildBean item = mAdapter.getItem(position);
                    ZClient.getService(SportService.class).addCircle(item.getId(), item.getFollow() == 1 ? 0 : 1).enqueue(addCircleCallback);

                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isFromPublishPostActivity) {
                    Intent data = new Intent();
                    data.putExtra("ChildBean", mAdapter.getItem(position));
                    getActivity().setResult(Activity.RESULT_OK, data);
                    getActivity().finish();
                }

            }
        });
    }


    @Override
    public void onClick(final View v) {
    }

    private ZCallback<ZResponse> addCircleCallback = new ZCallback<ZResponse>() {
        @Override
        public void onSuccess(ZResponse data) {
            ToastUtil.toast(data.getMessage());
            ((CircleActivity) getActivity()).loadData();
        }
    };

    public void setData(List<CircleBean.ChildBean> data) {
        mAdapter.setNewData(data);
    }

    public void setBoolean(boolean isFromPublishPostActivity) {
        this.isFromPublishPostActivity = isFromPublishPostActivity;
        mAdapter.setBoolean(isFromPublishPostActivity);
    }
}
