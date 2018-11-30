package com.miracle.base.network;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.base.adapter.RecyclerViewAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NaOH on 2018/8/3 15:11 (星期五).
 * Use ZPageLoadCallback instead.
 */
@Deprecated
public abstract class PageLoadCallback<T> implements Callback<T>, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerViewAdapter mAdapter;
    private int page = 1;
    private int pageSize = 20;
    private boolean isLoadMore;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Dialog dialog;
    //缓存key
    private String key;


    public PageLoadCallback(RecyclerViewAdapter adapter, RecyclerView recyclerView) {
        adapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter = adapter;
    }

    public PageLoadCallback(RecyclerViewAdapter adapter, RecyclerView recyclerView, String key) {
        adapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter = adapter;
        this.key = key;
    }

    public PageLoadCallback(RecyclerViewAdapter adapter, RecyclerView recyclerView, Dialog dialog) {
        adapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter = adapter;
        this.dialog = dialog;
    }

    public void initSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout = swipeRefreshLayout;
    }

    public void setSwipeRefreshLayout(SwipeRefreshLayout swipeRefreshLayout) {
        mSwipeRefreshLayout = swipeRefreshLayout;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        T body = response.body();
        if (body != null && body instanceof ZResponse) {
            ZResponse data = (ZResponse) body;
            switch (data.getCode()) {
                case 200:
                    specialHandle(data.getData());
                    page++;
                    if (isLoadMore) {
                        mAdapter.addData((List) data.getData());
                    } else {
                        mAdapter.setNewData((List) data.getData());
                    }
                    if (mAdapter.getData().size() == data.getTotal()) {
                        mAdapter.loadMoreEnd();
                    } else {
                        mAdapter.loadMoreComplete();
                    }
                    onFinish(call);
                    break;

                case 0:
                    mAdapter.loadMoreEnd();
                    onFinish(call);
                    break;

                default:
                    onFailure(call, new Throwable(response.message()));
                    break;
            }
        } else {
            onFailure(call, new Throwable(response.message()));
        }
    }

    public void specialHandle(Object data) {
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mAdapter.loadMoreFail();
        onFinish(call);
    }

    public void onFinish(Call<T> call) {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        if (dialog != null) {
            dialog.dismiss();
        }
        call.cancel();
    }

    @Override
    public void onRefresh() {
        if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing())
            mSwipeRefreshLayout.setRefreshing(true);
//        if (!NetStateUtils.isNetworkConnected(App.getApp())) {
//            ToastUtil.toast(App.getApp(), CommonUtils.getString(R.string.no_net));
//            mSwipeRefreshLayout.setRefreshing(false);
//            return;
//        }
        isLoadMore = false;
        page = 1;
        requestAction(page, pageSize);
    }

    @Override
    public void onLoadMoreRequested() {
        isLoadMore = true;
        requestAction(page, pageSize);
    }

    public abstract void requestAction(int page, int pageSize);
}
