package com.miracle.sport.schedule.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miracle.sport.schedule.bean.ClubeType;
import com.miracle.sport.schedule.bean.ClubeItem;
import com.miracle.sport.schedule.fragment.FragClubePostSS;
import com.miracle.sport.schedule.fragment.FragClubePostJF;
import com.miracle.sport.schedule.fragment.FragClubePostSCAndHot;

import java.util.ArrayList;
import java.util.List;

//频道横条
public class ClubeItemTabAdapter extends FragmentPagerAdapter {
    List<ClubeItem> datas = new ArrayList<>();
    public ClubeType parentType;

    public ClubeType getParentType() {
        return parentType;
    }

    public void setParentType(ClubeType parentType) {
        this.parentType = parentType;
    }

    public List<ClubeItem> getDatas() {
        return datas;
    }

    public void setDatas(List<ClubeItem> datas) {
        this.datas = datas;
    }

    public ClubeItemTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //根据赛事item 的type 返回各自的 fragment（服务器返回的数据类型不同）
        ClubeItem item = datas.get(position);
        Fragment frag = null;
        if(item.getType().equalsIgnoreCase(ClubeItem.TYPE_JF)){
            FragClubePostJF frag1 = new FragClubePostJF();
            frag1.setParentType(parentType);
            frag1.setReq(item);
            frag = frag1;
        }else if(item.getType().equalsIgnoreCase(ClubeItem.TYPE_SS) || item.getType().equalsIgnoreCase(ClubeItem.TYPE_ZG)){
            FragClubePostSS frag1 = new FragClubePostSS();
            frag1.setParentType(parentType);
            frag1.setReq(item);
            frag = frag1;
        }else{
            FragClubePostSCAndHot frag1 = new FragClubePostSCAndHot();
            frag1.setParentType(parentType);
            frag1.setReq(item);
            frag1.reqData();
            frag = frag1;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).getName();
    }

    public List getData() {
        return datas;
    }
}
