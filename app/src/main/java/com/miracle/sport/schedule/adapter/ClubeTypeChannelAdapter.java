package com.miracle.sport.schedule.adapter;

import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.miracle.base.BaseFragment;
import com.miracle.sport.schedule.fragment.HandleFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//频道横条
public class ClubeTypeChannelAdapter extends FragmentPagerAdapter {
    Map<Class<? extends BaseFragment>, String> clazzTabName = new HashMap<>();
    List<Class<? extends BaseFragment>> data = new ArrayList<>();
    Map<Class, BaseFragment> cache = new HashMap<>();
    Map<Class, Message> msgMap = new HashMap<>();

    public ClubeTypeChannelAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Class clazz = data.get(position);
        BaseFragment baseFragment = null;
        if(cache.containsKey(clazz)){
            baseFragment = cache.get(clazz);
        }else{
            try {
                baseFragment = (BaseFragment) clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            cache.put(clazz, baseFragment);
        }
        if(msgMap.containsKey(clazz) && baseFragment instanceof HandleFragment){
            HandleFragment handleFragment = (HandleFragment) baseFragment;
            handleFragment.uiHandler.sendMessage(msgMap.get(clazz));
        }
//        FragFootNewsItemList frag = new FragFootNewsItemList();
//        frag.setReqKey(data.get(position).getKey());
        return baseFragment;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return clazzTabName.get(data.get(position));
    }

    public List getData() {
        return data;
    }

    public void setData(Class<? extends BaseFragment> clazz, String name) {
        data.add(clazz);
        clazzTabName.put(clazz, name);
    }

    public void setMsg(Class<? extends BaseFragment> clazz, Message msg){
        msgMap.put(clazz, msg);
    }
}
