package com.miracle.sport.schedule.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.load.model.ModelLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.FragClubetypeListBinding;
import com.miracle.sport.schedule.activity.ClubeItemVPAct;
import com.miracle.sport.schedule.adapter.ClubeTypeAdapter;
import com.miracle.sport.schedule.bean.ClubeType;
import com.miracle.sport.schedule.net.FootClubServer;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;

//賽事 list
public class FragClubeTypeList extends BaseFragment<FragClubetypeListBinding> {
    ZPageLoadCallback<ZResponse<List<ClubeType>>> callback;
    ClubeTypeAdapter clubTypeAdapter;

    @Override
    public int getLayout() {
        return R.layout.frag_clubetype_list;
    }

    @Override
    public void initView() {
        clubTypeAdapter = new ClubeTypeAdapter(mContext);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setAdapter(clubTypeAdapter);
        clubTypeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                clubTypeAdapter.getData().get(position).getId();
                Intent intent = new Intent(mContext, ClubeItemVPAct.class);
                intent.putExtra(ClubeItemVPAct.EXTRA_ID, clubTypeAdapter.getData().get(position).getId());
                intent.putExtra(ClubeItemVPAct.EXTRA_NAME, clubTypeAdapter.getData().get(position).getName());
                startActivity(intent);
            }
        });

        callback = new ZPageLoadCallback<ZResponse<List<ClubeType>>>(clubTypeAdapter ,binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                if(page == 1)
                    callback.setCachKey("homepage_clubetype");
                else
                    callback.setCachKey("");
                Call<ZResponse<List<ClubeType>>> call = ZClient.getService(FootClubServer.class).getFootClubTypes(page, limit);
                RequestUtil.cacheUpdate(call, this);
            }

            @Override
            public void onFinish(Call call) {
                super.onFinish(call);
//                setUIStatus(ShowStat.NORMAL);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                super.onFailure(call, t);
//                setUIStatus(ShowStat.ERR);
            }
        };
        callback.setNetStatusUI(this);
        callback.initSwipeRefreshLayout(binding.swipeRefreshLayout);

        callback.onRefresh();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void loadData() {
        super.loadData();
        callback.onRefresh();
    }
}
