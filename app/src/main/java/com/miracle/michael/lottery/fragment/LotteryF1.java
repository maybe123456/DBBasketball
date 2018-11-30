package com.miracle.michael.lottery.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.BaseFragment;
import com.miracle.base.GOTO;
import com.miracle.base.network.PageLoadCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZService;
import com.miracle.base.switcher.GameActivity;
import com.miracle.base.util.ContextHolder;
import com.miracle.databinding.BannerLayoutBinding;
import com.miracle.databinding.F1LotteryBinding;
import com.miracle.michael.lottery.activity.LotteryNewsDetailActivity;
import com.miracle.michael.lottery.adapter.LotteryNewsListAdapter;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class LotteryF1 extends BaseFragment<F1LotteryBinding> {

    private LotteryNewsListAdapter mAdapter;
    private PageLoadCallback callBack;

    private List<String> images;

    @Override
    public int getLayout() {
        return R.layout.f1_lottery;
    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        mAdapter = new LotteryNewsListAdapter(mContext);
        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        addHeadView();

//        int resId = R.anim.layout_animation_fall_down;
//        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), resId);
//        binding.recyclerView.setLayoutAnimation(animation);
        initCallback();
    }

    private void addHeadView() {
        BannerLayoutBinding bannerBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.banner_layout, null, false);
        initBanner(bannerBinding);
        mAdapter.addHeaderView(bannerBinding.getRoot());
    }

    private void initBanner(BannerLayoutBinding bannerBinding) {
        images = new ArrayList<>();
//        images.add("file:///android_asset/lottery/1.png");
//        images.add("file:///android_asset/lottery/2.png");
//        images.add("file:///android_asset/lottery/3.png");
//        images.add("file:///android_asset/lottery/4.png");
//        images.add("file:///android_asset/lottery/5.png");
        bannerBinding.banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(ContextHolder.getContext()).load(path).into(imageView);
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
        binding.tvContactCustomerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GOTO.CustomerServiceActivity(mContext);
            }
        });
        callBack = new PageLoadCallback(mAdapter, binding.recyclerView) {
            @Override
            public void requestAction(int page, int limit) {
                ZClient.getService(ZService.class).getLotteryNewslList("cp", "wycp", page, limit).enqueue(callBack);
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
        binding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null)
                    drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(mContext, LotteryNewsDetailActivity.class).putExtra("id", mAdapter.getItem(position).getId()));
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private DrawerLayout drawerLayout;

    public LotteryF1 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
