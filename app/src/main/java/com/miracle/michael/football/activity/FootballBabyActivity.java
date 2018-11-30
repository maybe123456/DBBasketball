package com.miracle.michael.football.activity;

import android.support.v7.widget.LinearSnapHelper;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.databinding.ActivityFootballBabyBinding;
import com.miracle.michael.football.adapter.FootballBabyAdapter;
import com.miracle.michael.football.bean.FootballBabyBean;

import java.util.List;

/**
 * Created by Michael on 2018/10/25 21:34 (星期四)
 */
public class FootballBabyActivity extends BaseActivity<ActivityFootballBabyBinding> {
    private int class_id;
    private FootballBabyAdapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_football_baby;
    }

    @Override
    public void initView() {
        setTitle("足球宝贝");
        class_id = getIntent().getIntExtra("class_id", 0);
        initRecyclerView();
        reqData();
    }

    private void initRecyclerView() {
        binding.recyclerView.setAdapter(mAdapter = new FootballBabyAdapter(mContext));
        //滑动结束后停留在某张图片居中
        new LinearSnapHelper().attachToRecyclerView(binding.recyclerView);
    }


    private void reqData() {
        ZClient.getService(ZService.class).getFootballBabies(class_id).enqueue(new ZCallback<ZResponse<List<FootballBabyBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<FootballBabyBean>> data) {
                mAdapter.setNewData(data.getData());
            }
        });

    }


    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {

    }
}
