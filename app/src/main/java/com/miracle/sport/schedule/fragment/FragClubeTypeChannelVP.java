package com.miracle.sport.schedule.fragment;

import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.databinding.FragClubetypeVpBinding;
import com.miracle.sport.schedule.adapter.ClubeTypeChannelAdapter;

public class FragClubeTypeChannelVP extends BaseFragment<FragClubetypeVpBinding> {
    ClubeTypeChannelAdapter channelAdapter;
    FragmentManager supportFm;

    @Override
    public int getLayout() {
        return R.layout.frag_clubetype_vp;
    }

    @Override
    public void initView() {
        supportFm = getActivity().getSupportFragmentManager();
        binding.tablayout1.setupWithViewPager(binding.viewpager1);
//        binding.tablayout1.setTabMode(TabLayout.MODE_FIXED);
//        binding.tablayout1.setTabMode(TabLayout.MODE_SCROLLABLE);

        channelAdapter = new ClubeTypeChannelAdapter(supportFm);
        channelAdapter.setData(FragClubeTypeList.class,"赛程");
        channelAdapter.setData(FragClubePostSCAndHot.class,"热门");
        Message hotMsg = new Message();
        hotMsg.what = 2;
        channelAdapter.setMsg(FragClubePostSCAndHot.class, hotMsg);
        binding.viewpager1.setAdapter(channelAdapter);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onClick(View v) {

    }
}
