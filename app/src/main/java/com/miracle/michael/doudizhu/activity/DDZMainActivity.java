package com.miracle.michael.doudizhu.activity;

import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.GOTO;
import com.miracle.databinding.ActivityDdzMainBinding;
import com.miracle.michael.doudizhu.fragment.DDZF1;
import com.miracle.michael.doudizhu.fragment.DDZF2;
import com.miracle.michael.doudizhu.fragment.DDZF4;
import com.miracle.michael.football.fragment.FootballF3;

/**
 * Created by Michael on 2018/10/19 16:14 (星期五)
 */
public class DDZMainActivity extends BaseActivity<ActivityDdzMainBinding> {


    @Override
    public int getLayout() {
        return R.layout.activity_ddz_main;
    }

    @Override
    public void initView() {
        hideTitle();
        binding.zRadiogroup.setupWithContainerAndFragments(R.id.container, new DDZF1(), new DDZF2(), new FootballF3(), new DDZF4());
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
