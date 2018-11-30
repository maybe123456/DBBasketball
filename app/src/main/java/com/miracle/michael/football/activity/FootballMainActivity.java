package com.miracle.michael.football.activity;

import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.GOTO;
import com.miracle.databinding.ActivityFootballMainBinding;
import com.miracle.michael.football.fragment.FootballF1;
import com.miracle.michael.football.fragment.FootballF2;
import com.miracle.michael.football.fragment.FootballF3;
import com.miracle.michael.football.fragment.FootballF4;

/**
 * Created by Michael on 2018/10/19 16:14 (星期五)
 */
public class FootballMainActivity extends BaseActivity<ActivityFootballMainBinding> {


    @Override
    public int getLayout() {
        return R.layout.activity_football_main;
    }

    @Override
    public void initView() {
        hideTitle();
        binding.zRadiogroup.setupWithContainerAndFragments(R.id.container, new FootballF1(), new FootballF2(), new FootballF3(), new FootballF4());
//        GOTO.CircleTurntableActivity();
    }

    @Override
    public void initListener() {
        binding.tvContactCustomerService.setOnClickListener(this);
        binding.rlGroupChat.setOnClickListener(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvContactCustomerService:
                GOTO.CustomerServiceActivity(mContext);
                break;
            case R.id.rlGroupChat:
                GOTO.ChatActivity(mContext);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
    }
}
