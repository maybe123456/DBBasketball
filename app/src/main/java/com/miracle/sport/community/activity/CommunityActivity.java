package com.miracle.sport.community.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.BaseActivity;
import com.miracle.base.Constant;
import com.miracle.base.network.GlideApp;
import com.miracle.base.switcher.GameActivity;
import com.miracle.base.util.ContextHolder;
import com.miracle.databinding.ActivityCommunityBinding;
import com.miracle.sport.community.bean.MyCircleBean;
import com.miracle.sport.community.fragment.HotPostFragment;
import com.miracle.sport.community.fragment.LatestPostFragment;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 2018/11/1 11:05 (星期四)
 */
public class CommunityActivity extends BaseActivity<ActivityCommunityBinding> {
    private List<String> images;

    private HotPostFragment hotPostFragment;
    private LatestPostFragment latestPostFragment;

    @Override
    public int getLayout() {
        return R.layout.activity_community;
    }

    @Override
    public void initView() {
        binding.zRadiogroup.combineAnother(binding.zRadiogroupTop);
        binding.zRadiogroupTop.combineAnother(binding.zRadiogroup);
        binding.zRadiogroup.setUp(getSupportFragmentManager(), R.id.containerCommunity, hotPostFragment = new HotPostFragment().setParent(true), latestPostFragment = new LatestPostFragment().setParent(true));
        MyCircleBean circleBean = (MyCircleBean) getIntent().getSerializableExtra(Constant.MY_CIRCLE);
        setTitle(circleBean.getName());
        hotPostFragment.setCircleId(circleBean.getId());
        latestPostFragment.setCircleId(circleBean.getId());
        initBanner();
        binding.scrollView.setViews(binding.zRadiogroup, binding.zRadiogroupTop);
    }

    private void initBanner() {
        images = new ArrayList<>();
        images.add("file:///android_asset/lottery/19.jpg");
        images.add("file:///android_asset/lottery/20.jpg");
        binding.banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                GlideApp.with(ContextHolder.getContext()).load(path).placeholder(R.mipmap.defaule_img).into(imageView);

            }
        }).start();

        binding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (AppConfig.DBENTITY != null && AppConfig.DBENTITY.getAppBanner() == 1) {
                    startActivity(new Intent(mContext, GameActivity.class).putExtra("url", AppConfig.DBENTITY.getAppUrl()));
                }
            }
        });
    }

    @Override
    public void initListener() {
//        setRight("发布");
//        setRightClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mContext, PublishPostActivity.class));
//            }
//        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (hotPostFragment.isVisible())
                    hotPostFragment.loadData();
                if (latestPostFragment.isVisible())
                    latestPostFragment.loadData();
            }
        });
    }

    @Override
    public void loadData() {
        if (hotPostFragment.isVisible())
            hotPostFragment.loadData();
        if (latestPostFragment.isVisible())
            latestPostFragment.loadData();
    }

    @Override
    public void onClick(View v) {

    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return binding.swipeRefreshLayout;
    }
}
