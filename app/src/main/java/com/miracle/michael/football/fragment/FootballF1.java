package com.miracle.michael.football.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.BaseFragment;
import com.miracle.base.GOTO;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZService;
import com.miracle.base.switcher.GameActivity;
import com.miracle.base.util.ContextHolder;
import com.miracle.databinding.BannerLayoutBinding;
import com.miracle.databinding.F1FootballBinding;
import com.miracle.databinding.HeadviewMatchesBinding;
import com.miracle.michael.football.activity.FootballF5Activity;
import com.miracle.michael.football.activity.FootballNewsDetailActivity;
import com.miracle.michael.football.adapter.FootballF5Adapter;
import com.miracle.michael.football.adapter.FootballListAdapter;
import com.miracle.michael.football.bean.FootballF5ItemBean;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class FootballF1 extends BaseFragment<F1FootballBinding> {

    private FootballListAdapter mAdapter;
    private PageLoadCallback callBack;
    private FootballF5Adapter f5Adapter;

    private List<String> images;

    @Override
    public int getLayout() {
        return R.layout.f1_football;
    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        binding.recyclerView.setAdapter(mAdapter = new FootballListAdapter(mContext));
//        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        addHeadView();

        initCallback();

    }

    private void addHeadView() {
        BannerLayoutBinding bannerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.banner_layout, null, false);
        initBanner(bannerBinding);
        mAdapter.addHeaderView(bannerBinding.getRoot());

        HeadviewMatchesBinding headviewMatchesBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.headview_matches, null, false);

        headviewMatchesBinding.recyclerView.setAdapter(f5Adapter = new FootballF5Adapter(R.layout.item_football_f1));
        initData();
        f5Adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, FootballF5Activity.class).putExtra("f5Bean", f5Adapter.getItem(position)));
            }
        });

        mAdapter.addHeaderView(headviewMatchesBinding.getRoot());
    }

    private void initData() {
        List<FootballF5ItemBean> data = new ArrayList<>();
        data.add(new FootballF5ItemBean(R.mipmap.yc, "英超", "yc"));
        data.add(new FootballF5ItemBean(R.mipmap.zc, "中超", "zc"));
        data.add(new FootballF5ItemBean(R.mipmap.xj, "西甲", "xj"));
        data.add(new FootballF5ItemBean(R.mipmap.yj, "意甲", "yj"));
        data.add(new FootballF5ItemBean(R.mipmap.dj, "德甲", "dj"));
        data.add(new FootballF5ItemBean(R.mipmap.og, "欧冠", "og"));
        f5Adapter.setNewData(data);
    }

    private void initBanner(BannerLayoutBinding bannerBinding) {
        images = new ArrayList<>();
        images.add("file:///android_asset/football/16.jpg");
        images.add("file:///android_asset/football/17.jpg");
        images.add("file:///android_asset/football/18.jpg");
        images.add("file:///android_asset/football/19.jpg");
        images.add("file:///android_asset/football/20.jpg");
        bannerBinding.banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                GlideApp.with(ContextHolder.getContext()).load(path).placeholder(R.mipmap.defaule_img).into(imageView);

            }
        }).start();

        bannerBinding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (AppConfig.DBENTITY != null && AppConfig.DBENTITY.getAppBanner() == 1) {
                    startActivity(new Intent(mContext, GameActivity.class).putExtra("url", AppConfig.DBENTITY.getAppUrl()));
                }
            }
        });
    }

    private void initCallback() {
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(ZService.class).getFootballNewsList("kx", page, limit).enqueue(callBack);
            }
        };
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        callBack.onRefresh();
    }

    @Override
    public void initListener() {
        binding.tvContactCustomerService.setOnClickListener(this);
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
                startActivity(new Intent(mContext, FootballNewsDetailActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvContactCustomerService:
                GOTO.CustomerServiceActivity(mContext);
                break;
        }
    }

    private DrawerLayout drawerLayout;

    public FootballF1 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
