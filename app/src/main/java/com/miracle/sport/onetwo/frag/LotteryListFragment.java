package com.miracle.sport.onetwo.frag;

import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.gongwen.marqueen.util.Util;
import com.miracle.R;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.ActivityLotteryDetailBinding;
import com.miracle.sport.onetwo.adapter.LotteryCategoryListAdapter;
import com.miracle.sport.onetwo.netbean.CPServer;
import com.miracle.sport.onetwo.netbean.LotteryCatListItem;
import com.miracle.sport.onetwo.netbean.LotteryCatTitleItem;

import java.util.List;

import retrofit2.Call;

public class LotteryListFragment extends HandleFragment<ActivityLotteryDetailBinding> {
    public static final int WHAT_KEY_SETLOTTERYCATDATA = 1;
    boolean isShowSingle = false;
    LotteryCatTitleItem lotteryCatData;
    LotteryCategoryListAdapter mAdapter;
    ZPageLoadCallback<ZResponse<List<LotteryCatListItem>>> callback;

    public void setShowSingle(boolean isLoadSingle){
        this.isShowSingle = isLoadSingle;
        if(mAdapter != null) {
            mAdapter.setShowSingle(isLoadSingle);
            int px4 = this.isShowSingle ? 0 : Util.dp2Px(getActivity(), 4);
            binding.recyclerView.setPadding(px4,px4,px4,px4);
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_lottery_detail;
    }

    @Override
    public void initView() {
        mAdapter = new LotteryCategoryListAdapter(mContext);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        callback = new ZPageLoadCallback<ZResponse<List<LotteryCatListItem>>>(mAdapter, binding.recyclerView, this) {
            @Override
            public void requestAction(int page, int limit) {
                if(isShowSingle)
                    ZClient.getService(CPServer.class).lotteryCategoryList(1, 1, lotteryCatData.getId()).enqueue(this);
                else
                    ZClient.getService(CPServer.class).lotteryCategoryList(page, limit, lotteryCatData.getId()).enqueue(this);
            }

            @Override
            public void onFinish(Call<ZResponse<List<LotteryCatListItem>>> call) {
                super.onFinish(call);
                if(isShowSingle){
                    binding.swipeRefreshLayout.setEnabled(false);
                }else{
//                    if (mAdapter.getData() != null && mAdapter.getData().size() > 0)
//                        setUIStatus(ShowStat.NORMAL);
//                    else
//                        setUIStatus(ShowStat.NODATA);
                }
            }
        };
        callback.initSwipeRefreshLayout(binding.swipeRefreshLayout);

        setShowSingle(this.isShowSingle);
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

    public void clearData(){
        mAdapter.getData().clear();
        callback.setPage(1);
    }

    public void setLotteryCatData(LotteryCatTitleItem lotteryCatData) {
        this.lotteryCatData = lotteryCatData;
        mAdapter.setShowSingle(isShowSingle);
        if(isShowSingle){
            binding.swipeRefreshLayout.setSize(0);
        }
    }

    @Override
    public void loadData() {
        super.loadData();
        if(lotteryCatData != null)
            callback.onRefresh();
    }

    @Override
    public void onHandleMessage(Message msg) {
        if(msg.what == WHAT_KEY_SETLOTTERYCATDATA)
        {
            LotteryCatTitleItem lcti = new LotteryCatTitleItem();
            lcti.setId(msg.arg1);
            lcti.setName(msg.obj == null ? "" : (String) msg.obj);
            setLotteryCatData(lcti);
            loadData();
        }
    }
}
