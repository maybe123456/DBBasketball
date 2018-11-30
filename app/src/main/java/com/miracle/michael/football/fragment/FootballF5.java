package com.miracle.michael.football.fragment;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.databinding.F5FootballBinding;
import com.miracle.michael.football.activity.FootballF5Activity;
import com.miracle.michael.football.adapter.FootballF5Adapter;
import com.miracle.michael.football.bean.FootballF5ItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2018/10/21 20:11 (星期日)
 */
public class FootballF5 extends BaseFragment<F5FootballBinding> {
    private FootballF5Adapter mAdapter;

    @Override
    public int getLayout() {
        return R.layout.f5_football;
    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        binding.recyclerView.setAdapter(mAdapter = new FootballF5Adapter(R.layout.item_football_f5));
        initData();
    }

    private void initData() {
        List<FootballF5ItemBean> data = new ArrayList<>();
        data.add(new FootballF5ItemBean(R.mipmap.yc, "英超", "yc"));
        data.add(new FootballF5ItemBean(R.mipmap.zc, "中超", "zc"));
        data.add(new FootballF5ItemBean(R.mipmap.xj, "西甲", "xj"));
        data.add(new FootballF5ItemBean(R.mipmap.yj, "意甲", "yj"));
        data.add(new FootballF5ItemBean(R.mipmap.dj, "德甲", "dj"));
        data.add(new FootballF5ItemBean(R.mipmap.og, "欧冠", "og"));
        mAdapter.setNewData(data);
    }

    @Override
    public void initListener() {
        binding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null)
                    drawerLayout.openDrawer(Gravity.START);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, FootballF5Activity.class).putExtra("f5Bean", mAdapter.getItem(position)));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private DrawerLayout drawerLayout;

    public FootballF5 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
