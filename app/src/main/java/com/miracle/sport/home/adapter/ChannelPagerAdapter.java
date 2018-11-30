package com.miracle.sport.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.miracle.base.BaseFragment;
import com.miracle.sport.home.bean.Channel;
import com.miracle.sport.home.fragment.ChannelHomeFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * @author ChayChan
 * @description: 频道的adapter
 * @date 2017/6/16  9:45
 */

public class ChannelPagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragments;
    private List<Channel> mChannels;

    public ChannelPagerAdapter(List<BaseFragment> fragmentList, List<Channel> channelList, FragmentManager fm) {
        super(fm);
        mFragments = fragmentList != null ? fragmentList : new ArrayList<BaseFragment>();
        mChannels = channelList != null ? channelList : new ArrayList<Channel>();

    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).name;
    }

    @Override
    public int getItemPosition(Object object) {
        //        if (mChildCount > 0) {
        //            mChildCount--;
        return POSITION_NONE;
        //        }
        //        return super.getItemPosition(object);
    }
}
