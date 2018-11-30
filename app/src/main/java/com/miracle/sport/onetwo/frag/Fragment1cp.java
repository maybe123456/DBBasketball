package com.miracle.sport.onetwo.frag;

import android.view.View;
import android.widget.AdapterView;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.Fragment1cpBinding;
import com.miracle.sport.onetwo.adapter.LCTIndexAdapter;
import com.miracle.sport.onetwo.netbean.CPServer;
import com.miracle.sport.onetwo.netbean.LotteryCatTitleItem;
import com.nightonke.boommenu.Util;

import java.util.List;

//left type chose, right list
public class Fragment1cp extends BaseFragment<Fragment1cpBinding> {
    LotteryListFragment fragment;
    private LCTIndexAdapter indexAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment1cp;
    }

    @Override
    public void initView() {
        showTitle();
        setTitle("开奖历史");
//        setTitleIfNoSet(""+getString(R.string.main_title_1));
        fragment = (LotteryListFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.categoryDetailFragment2);

        binding.indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fragment.clearData();
                fragment.setLotteryCatData(indexAdapter.getDatas().get(position));
                fragment.loadData();
                indexAdapter.setSelectPosition(position);
            }
        });

        int padding = Util.dp2px(6);
        binding.getRoot().findViewById(R.id.frag1cp_continer).setPadding(padding,padding,padding,padding);

        indexAdapter = new LCTIndexAdapter(mContext);
        binding.indexListView.setAdapter(indexAdapter);

        initCallback();
        requData();
    }

    private void requData(){
//        callBack.onRefresh();
        ZClient.getService(CPServer.class).lotteryCategory().enqueue(new ZCallback<ZResponse<List<LotteryCatTitleItem>>>(this) {
            @Override
            public void onSuccess(ZResponse<List<LotteryCatTitleItem>> data) {
                indexAdapter.update(data.getData());
                indexAdapter.notifyDataSetChanged();
                if(data.getData() != null && data.getData().size() > 0){
                    fragment.setLotteryCatData(indexAdapter.getDatas().get(0));
                    fragment.loadData();
                }
            }

//            @Override
//            public void onFinish() {
//                super.onFinish();
//                if(indexAdapter.getDatas() != null && indexAdapter.getDatas().size() > 0)
//                    setShowNodata(false);
//                else
//                    setShowNodata(true);
//            }
        });
    }

    private void initCallback() {
//        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
//            @Override
//            public void requestAction(int page, int limit) {
//                ZClient.getService(CPServer.class).cpTitleList().enqueue(this);
//            }
//
//            @Override
//            public void onFinish(Call call, Response response) {
//                super.onFinish(call, response);
//                if(mAdapter.getData() != null && mAdapter.getData().size() > 0)
//                    setShowNodata(false);
//                else
//                    setShowNodata(true);
//            }
//        };
//        callBack.setSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
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
        requData();
    }
}
