package com.miracle.base.network;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.App;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.NetStateUtils;
import com.miracle.base.util.ToastUtil;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.home.bean.HomeBean;

import java.util.List;

import retrofit2.Call;

/**
 * Created by NaOH on 2018/8/3 15:11 (星期五).
 */
public abstract class ZPageLoadDataCallback<T> extends ZCallback<T> implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerViewAdapter mAdapter;
    private int page = 1;
    private int pageSize = 20;
    private boolean isLoadMore;


    public ZPageLoadDataCallback(RecyclerViewAdapter adapter, RecyclerView recyclerView) {
        adapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter = adapter;
    }


    /**
     * 配置SwipeRefreshLayout,刷新自动请求数据
     *
     * @param swipeRefreshLayout
     */
    public void initSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout = swipeRefreshLayout;
    }


    /**
     * 自定义每页请求的数量
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    protected void onSuccess(T data) {
        ZResponse zResponse = ((ZResponse) data);
        //非200即0
        if (zResponse.getCode() == 200) {
            List<Football> data1 = ((HomeBean) zResponse.getData()).getData();
            page++;
            if (isLoadMore) {
                mAdapter.addData(data1);
            } else {
                mAdapter.setNewData(data1);
            }
            if (mAdapter.getData().size() == zResponse.getTotal()) {
                mAdapter.loadMoreEnd();
            } else {
                mAdapter.loadMoreComplete();
            }
        } else {
            mAdapter.loadMoreEnd();
        }
    }


    @Override
    public void onFailure(Call call, Throwable t) {
        super.onFailure(call, t);
        mAdapter.loadMoreFail();
    }


    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
//        if (!NetStateUtils.isNetworkConnected(App.getApp())) {
//            ToastUtil.toast(App.getApp(), CommonUtils.getString(R.string.no_net));
//            mSwipeRefreshLayout.setRefreshing(false);
//            onFailure(null, new Throwable("当前无网络"));
//            return;
//        }
        isLoadMore = false;
        page = 1;
        requestAction(page, pageSize);
    }

    @Override
    public void onLoadMoreRequested() {
        if (!NetStateUtils.isNetworkConnected(App.getApp())) {
            ToastUtil.toast(App.getApp(), CommonUtils.getString(R.string.no_net));
            mAdapter.loadMoreFail();
            return;
        }
        isLoadMore = true;
        requestAction(page, pageSize);
    }

    public abstract void requestAction(int page, int pageSize);
}
