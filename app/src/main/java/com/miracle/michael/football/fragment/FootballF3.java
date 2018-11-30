package com.miracle.michael.football.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.view.ItemBar;
import com.miracle.databinding.BannerLayoutBinding;
import com.miracle.databinding.F3FootballBinding;
import com.miracle.databinding.HeadviewMatchesBinding;
import com.miracle.databinding.RecyclerBinding;
import com.miracle.michael.football.activity.FootballBabyActivity;
import com.miracle.michael.football.activity.FootballClubDetailActivity;
import com.miracle.michael.football.activity.FootballF5Activity;
import com.miracle.michael.football.adapter.FootballBabyAlbumListAdapter;
import com.miracle.michael.football.adapter.FootballCubListAdapter;
import com.miracle.michael.football.adapter.FootballF5Adapter;
import com.miracle.michael.football.bean.FootballClubBean;

import java.util.List;

public class FootballF3 extends BaseFragment<F3FootballBinding> {

    private FootballCubListAdapter mAdapter;
    private FootballBabyAlbumListAdapter bAdapter;
    private PageLoadCallback callBack;

    @Override
    public int getLayout() {
        return R.layout.f3_football;
    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        binding.rvFootballBabyAlbum.setAdapter(bAdapter = new FootballBabyAlbumListAdapter(mContext));
        binding.rvFootballBabyAlbum.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
        binding.rvFootballBabyAlbum.setLayoutAnimation(animation);
        addHeadView();
        initCallback();
    }

    private void addHeadView() {
        RecyclerBinding clubBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.recycler, null, false);
        clubBinding.recyclerView.setAdapter(mAdapter = new FootballCubListAdapter());
        clubBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        bAdapter.addHeaderView(clubBinding.getRoot());
        CommonUtils.setMargins(clubBinding.recyclerView, 0, 10, 0, 0);

        ItemBar itemBar = new ItemBar(mContext);
        itemBar.setLeftIcon(CommonUtils.getString(R.string.icon_football_baby));
        itemBar.setText("足球宝贝");
        itemBar.setRightVisibility(View.GONE);
        bAdapter.addHeaderView(itemBar);
    }

    private void initCallback() {
        callBack = new PageLoadCallback(bAdapter, binding.rvFootballBabyAlbum) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(ZService.class).getFootballBabyAlbums(page, limit).enqueue(callBack);
            }
        };
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    private void reqData() {
        ZClient.getService(ZService.class).getFootballClubList().enqueue(new ZCallback<ZResponse<List<FootballClubBean>>>(binding.swipeRefreshLayout) {
            @Override
            public void onSuccess(ZResponse<List<FootballClubBean>> data) {
                mAdapter.setNewData(data.getData());
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        reqData();
        callBack.onRefresh();
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
                startActivity(new Intent(mContext, FootballClubDetailActivity.class).putExtra("clubkey", mAdapter.getItem(position)));
            }
        });
        bAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, FootballBabyActivity.class).putExtra("class_id", bAdapter.getItem(position).getClass_id()));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private DrawerLayout drawerLayout;

    public FootballF3 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
