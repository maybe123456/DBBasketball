package com.miracle.sport.onetwo.frag;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.sport.onetwo.adapter.CPHallListAdapter;
import com.miracle.sport.onetwo.entity.CpHall;
import com.miracle.sport.onetwo.netbean.CPServer;
import com.miracle.sport.onetwo.netbean.LotteryCatTitleItem;
import com.miracle.databinding.ActivityLotteryDetailBinding;
import com.nightonke.boommenu.Util;

import java.util.List;

import retrofit2.Call;

//ActivityLotteryDetailBinding
public class CpHallFragment extends BaseFragment<ActivityLotteryDetailBinding> {
    LotteryCatTitleItem lotteryCatData;

    CPHallListAdapter mAdapter;
    ZPageLoadCallback<ZResponse<List<CpHall>>> callback;

    @Override
    public int getLayout() {
        return R.layout.activity_lottery_detail;
    }

    @Override
    public void initView() {
        showTitle();
        setTitle("彩票大厅");
        mAdapter = new CPHallListAdapter(mContext);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        int px = Util.dp2px(4);
        binding.recyclerView.setPadding(px,px,px,px);

        reqData();
        loadData();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    public LotteryCatTitleItem getLotteryCatData() {
        return lotteryCatData;
    }

    public void reqData() {
        this.lotteryCatData = lotteryCatData;
//        setTitle("" + lotteryCatData.getName());
//        final int id = lotteryCatData.getId();
        callback = new ZPageLoadCallback<ZResponse<List<CpHall>>>(mAdapter, binding.recyclerView, this) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(CPServer.class).cpHall().enqueue(callback);
            }

            @Override
            public void onFinish(Call<ZResponse<List<CpHall>>> call) {
                super.onFinish(call);
//                if (mAdapter.getData() != null && mAdapter.getData().size() > 0)
//                    setUIStatus(ShowStat.NORMAL);
//                else
//                    setUIStatus(ShowStat.NODATA);
            }
        };
//        callback.setSwipeRefreshLayout(binding.swipeRefreshLayout);
        callback.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void loadData() {
        super.loadData();
        callback.onRefresh();
    }
}
