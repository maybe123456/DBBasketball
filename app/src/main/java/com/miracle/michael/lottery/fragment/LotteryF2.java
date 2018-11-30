package com.miracle.michael.lottery.fragment;

import android.view.View;
import android.widget.AdapterView;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.databinding.F2LotteryBinding;
import com.miracle.michael.lottery.adapter.LotteryIndexAdapter;
import com.miracle.michael.lottery.bean.LotteryBean;

import java.util.List;


public class LotteryF2 extends BaseFragment<F2LotteryBinding> {
    private LotteryIndexAdapter indexAdapter;

    private LotteryF2Child detailFragment;


    @Override
    public int getLayout() {
        return R.layout.f2_lottery;
    }

    @Override
    public void initView() {
        indexAdapter = new LotteryIndexAdapter(mContext);
        binding.indexListView.setAdapter(indexAdapter);
        detailFragment = (LotteryF2Child) getActivity().getSupportFragmentManager().findFragmentById(R.id.categoryDetailFragment);
    }

    private void reqData() {
        ZClient.getService(ZService.class).getLotteryList().enqueue(new ZCallback<ZResponse<List<LotteryBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<LotteryBean>> data) {
                indexAdapter.update(data.getData());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        reqData();
    }

    @Override
    public void initListener() {
        binding.indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long itemId) {
                indexAdapter.setSelectPosition(position);
                detailFragment.setReqKey(indexAdapter.getItem(position));
            }
        });

    }


    @Override
    public void onClick(View view) {

    }

}
