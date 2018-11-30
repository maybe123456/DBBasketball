package com.miracle.sport.home.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.Constant;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZPageLoadDataCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.FragmentCategoryHomeBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.home.activity.SimpleWebCommentActivity;
import com.miracle.sport.home.adapter.HomeListAdapter;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.home.bean.HomeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/5.
 */

public class ChannelHomeFristFragment extends BaseFragment<FragmentCategoryHomeBinding>{


    private HomeListAdapter mAdapter;
    private ZPageLoadCallback callBack;

    private int reqKey = 1;
    /**页面是否是首次加载*/
    private boolean isFirst = true;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_category_home;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isFirst && isVisibleToUser && getActivity()!=null){
            callBack.onRefresh();
        }
    }

    @Override
    public void initView() {

//        reqKey =  Integer.parseInt(getArguments().getString(Constant.CHANNEL_CODE));
        reqKey = Integer.parseInt(getArguments().getString(Constant.CHANNEL_CODE));

        binding.recyclerView.setAdapter(mAdapter = new HomeListAdapter(mContext));
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        binding.recyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
//        binding.recyclerView.setHasFixedSize(true);
        binding.tvCategoryTitle.setText("社会");
        initCallback();
        callBack.onRefresh();

    }

    private void initCallback() {

//        HashMap<String, String> params = new HashMap<>();
//        params.put("class_id", reqKey+"");
//        params.put("page", 1+"");
//        params.put("pageSize", 10+"");
//
//        OkGo.<ResultForJob<HomeBean>>post(UrlConstants.POST_SPORT_LIST)
//                .tag(this)
//                .params(params,true)
//                .cacheKey(CacheContents.HOME_SPORT_LIST+reqKey)
//                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
//                .execute(new EncryptCallback<ResultForJob<HomeBean>>() {
//                    @Override
//                    public void onSuccess(Response<ResultForJob<HomeBean>> response) {
//                        mAdapter.setNewData(response.body().getData().getData());
//                    }
//
//                    @Override
//                    public void onError(Response<ResultForJob<HomeBean>> response) {
//                        super.onError(response);
//                        ToastUtil.toast("shib");
//                    }
//
//                    @Override
//                    public void onCacheSuccess(Response<ResultForJob<HomeBean>> response) {
//                        super.onCacheSuccess(response);
//                        mAdapter.setNewData(response.body().getData().getData());
//                    }
//                });

//        callBack = new PageLoadDataCallback(mAdapter, binding.recyclerView) {
//            @Override
//            public void requestAction(int page, int limit) {
//                ZClient.getService(SportService.class).getNewsList(reqKey, page, limit).enqueue(callBack);
//            }
//        };
        callBack=new ZPageLoadCallback<ZResponse<List<Football>>>(mAdapter,binding.recyclerView) {
            @Override
            public void requestAction(int page, int pageSize) {
                callBack.setCachKey("ChanneHomeFragment" + reqKey+page);
                RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getNewsSpotrList(reqKey, page, pageSize),callBack);
            }

            @Override
            protected void onSuccess(ZResponse<List<Football>> data) {
                super.onSuccess(data);
                isFirst = false;
            }

        };
//        callBack = new ZPageLoadCallback<ZResponse<HomeBean>>(mAdapter, binding.recyclerView) {
//            @Override
//            public void requestAction(int page, int pageSize) {
//                RequestUtil.request1(ZClient.getService(SportService.class).getNewsList(reqKey, page, pageSize), callBack);
//            }
//        };
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                startActivity(new Intent(mContext, SimpleWebChessActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
                startActivity(new Intent(mContext, SimpleWebCommentActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {

    }

//    public void setReqKey(FootballKey footballKey) {
//        this.reqKey = footballKey.getId();
//        binding.tvCategoryTitle.setText(footballKey.getName());
//        callBack.onRefresh();
//    }
}
