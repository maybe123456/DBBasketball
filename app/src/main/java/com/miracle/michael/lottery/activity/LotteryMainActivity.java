package com.miracle.michael.lottery.activity;

import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.GOTO;
import com.miracle.databinding.ActivityLotteryMainBinding;
import com.miracle.michael.lottery.fragment.LotteryF1;
import com.miracle.michael.lottery.fragment.LotteryF3;
import com.miracle.michael.lottery.fragment.LotteryF5;
import com.miracle.michael.lottery.fragment.LotteryF7;

;


public class LotteryMainActivity extends BaseActivity<ActivityLotteryMainBinding> {

    @Override
    public int getLayout() {
        return R.layout.activity_lottery_main;
    }

    @Override
    public void initView() {
        hideTitle();
        binding.zRadiogroup.setupWithContainerAndFragments(R.id.container, new LotteryF1(), new LotteryF3(), new LotteryF7(), new LotteryF5());
//        if (!SQLiteUtil.getBoolean(SQLiteKey.HAS_DRAWED + AppConfig.USER_ID))
//            GOTO.CircleTurntableActivity();
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

}
