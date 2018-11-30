package com.miracle.sport.onetwo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miracle.sport.onetwo.frag.FragCpItemList;
import com.miracle.sport.onetwo.netbean.CpTitleItem;

import java.util.ArrayList;
import java.util.List;

public class CpTitleListChannelAdapter extends FragmentPagerAdapter {
    List<CpTitleItem> data = new ArrayList<>();

    public CpTitleListChannelAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        FragCpItemList categoryDetailFragment = new FragCpItemList();
        categoryDetailFragment.setReqKey(data.get(position).getType());
        categoryDetailFragment.setShowBanner(false);
        return categoryDetailFragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position).getName();
    }

    public List<CpTitleItem> getData() {
        return data;
    }

    public void setData(List<CpTitleItem> data) {
        this.data = data;
    }
}
