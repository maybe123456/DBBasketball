package com.miracle.sport.community.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.BaseFragment;
import com.miracle.base.Constant;
import com.miracle.base.GOTO;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.switcher.GameActivity;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ContextHolder;
import com.miracle.databinding.FragmentCommunityBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.activity.CircleActivity;
import com.miracle.sport.community.activity.PublishPostActivity;
import com.miracle.sport.community.bean.MyCircleBean;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Michael on 2018/10/29 11:23 (星期一)
 */
public class CommunityFragment extends BaseFragment<FragmentCommunityBinding> {
    private List<String> images;

    private HotPostFragment hotPostFragment;
    private LatestPostFragment latestPostFragment;

    private MyCircleAdapterWithAdd myCircleAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment_community;
    }

    @Override
    public void initView() {
        binding.zRadiogroupTop.combineAnother(binding.zRadiogroup);
        binding.zRadiogroup.combineAnother(binding.zRadiogroupTop);
        binding.zRadiogroup.setUp(getChildFragmentManager(), R.id.containerCommunity, hotPostFragment = new HotPostFragment(), latestPostFragment = new LatestPostFragment());
        binding.recyclerView.setAdapter(myCircleAdapter = new MyCircleAdapterWithAdd());
        initBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
        reqMyCircle();
    }

    private void reqMyCircle() {
        if (TextUtils.isEmpty(CommonUtils.getUserId())) {
            return;
        }

        RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getMyCircleList(), new ZCallback<ZResponse<List<MyCircleBean>>>("mycircle") {
            @Override
            public void onSuccess(ZResponse<List<MyCircleBean>> data) {
                myCircleAdapter.setNewData(null);
                myCircleAdapter.addData((MyCircleBean) null);
                myCircleAdapter.addData(0, data.getData());
            }
        });
//        ZClient.getService(SportService.class).getMyCircleList().enqueue(new ZCallback<ZResponse<List<MyCircleBean>>>() {
//            @Override
//            public void onSuccess(ZResponse<List<MyCircleBean>> data) {
//                myCircleAdapter.addData(0, data.getData());
//            }
//        });
    }

    private void initBanner() {
        images = new ArrayList<>();
        images.add("file:///android_asset/lottery/banner04.png");
        images.add("file:///android_asset/lottery/banner05.png");
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

        binding.titleBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivityForResult(new Intent(mContext, PublishPostActivity.class), Constant.REQUSET_CODE_PUBLISH_POST);
                }
            }
        });
        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (hotPostFragment.isVisible())
                    hotPostFragment.loadData();
                if (latestPostFragment.isVisible())
                    latestPostFragment.loadData();
                reqMyCircle();
            }
        });

        myCircleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.rlAdd) {
                    startActivity(new Intent(mContext, CircleActivity.class));
                }
            }
        });

        myCircleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                hotPostFragment.switchCircleId(myCircleAdapter.getItem(position).getId());
                latestPostFragment.switchCircleId(myCircleAdapter.getItem(position).getId());
            }
        });
        binding.ibMyCircle.setOnClickListener(this);

        binding.scrollView.setViews(binding.zRadiogroup, binding.zRadiogroupTop);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibMyCircle:
                if (CommonUtils.getUser() == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    startActivity(new Intent(mContext, CircleActivity.class));
                }
                break;
        }
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return binding.swipeRefreshLayout;
    }

    private final class MyCircleAdapterWithAdd extends BaseQuickAdapter<MyCircleBean, BaseViewHolder> {

        MyCircleAdapterWithAdd() {
            super(R.layout.item_mycircle_withadd);
        }

        @Override
        protected void convert(BaseViewHolder helper, MyCircleBean bean) {
            helper.addOnClickListener(R.id.rlAdd);
            if (bean == null) {
                helper.setGone(R.id.rlItem, false);
                helper.setGone(R.id.rlAdd, true);
            } else {
                helper.setGone(R.id.rlItem, true);
                helper.setGone(R.id.rlAdd, false);
                helper.setText(R.id.tvCircleName, bean.getName());
                GlideApp.with(mContext).load(bean.getPic()).placeholder(R.mipmap.defaule_img).into((ImageView) helper.getView(R.id.ivCircleLogo));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.REQUSET_CODE_PUBLISH_POST && resultCode == RESULT_OK) {
            binding.zRadiogroup.setCheck(1);
        }

    }
}
