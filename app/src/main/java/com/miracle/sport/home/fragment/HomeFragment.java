package com.miracle.sport.home.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gongwen.marqueen.SimpleMF;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.BaseFragment;
import com.miracle.base.Constant;
import com.miracle.base.http.CacheConstant;
import com.miracle.base.http.Common.EncryptCallback;
import com.miracle.base.http.model.bean.PageResultForJob;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.switcher.GameActivity;
import com.miracle.base.util.CommonUtils;
import com.miracle.base.util.ContextHolder;
import com.miracle.base.util.ToastUtil;
import com.miracle.databinding.FragmentHomeBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.common.constant.CacheContents;
import com.miracle.sport.common.constant.UrlConstants;
import com.miracle.sport.home.ServiceHome;
import com.miracle.sport.home.adapter.ChannelPagerAdapter;
import com.miracle.sport.home.bean.Channel;
import com.miracle.sport.home.bean.ChannerlKey;
import com.miracle.sport.home.listener.OnChannelListener;
import com.miracle.sport.home.util.PreUtils;
import com.miracle.sport.home.util.UIUtils;
import com.yanzhenjie.sofia.Sofia;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;



public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements OnChannelListener {
    private List<String> images;
    private List<Channel> mSelectedChannels = new ArrayList<>();
    private List<Channel> mUnSelectedChannels = new ArrayList<Channel>();
    private List<ChannerlKey> mNetChannels = new ArrayList<>();
    private List<BaseFragment> mChannelFragments = new ArrayList<>();
    private ChannelPagerAdapter mChannelPagerAdapter;
    private Gson mGson = new Gson();
    private String[] mChannelCodes;
    private boolean isCache;

    @Override
    public int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Sofia.with(getActivity()).statusBarBackground(CommonUtils.getColor(R.color.colorPrimaryDark)).statusBarLightFont();
    }

    @Override
    public void initView() {
        reqData();
        initBanner();
        List<String> textList = Arrays.asList("欢迎来到应用", "可在社区中讨论赛事相关信息", "期待您加入我们");
        initMard(textList);
//        initChannelData();
//        initChannelFragments();

    }

    private void initBanner() {
        images = new ArrayList<>();
        images.add("file:///android_asset/lottery/banner06.png");
        images.add("file:///android_asset/lottery/banner07.png");
//        images.add("file:///android_asset/lottery/18.jpg");
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

    private void initMard(List<String> list) {
        SimpleMF<String> marqueeFactory2 = new SimpleMF(mContext);
        marqueeFactory2.setData(list);
        binding.marqueeView3.setMarqueeFactory(marqueeFactory2);
        binding.marqueeView3.stopFlipping();
        binding.marqueeView3.startFlipping();
    }

    private void reqData() {

//        HashMap<String, String> params = new HashMap<>();
//        params.put("type", "zcsj");

//        OkGo.<PageResultForJob<ChannerlKey>>post(UrlConstants.POST_GOOD_CAIPIAO)
//                .tag(this)
////                .params(params,true)
//                .cacheKey(CacheContents.HOME_SPORT_TYPE)
//                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
//                .execute(new EncryptCallback<PageResultForJob<ChannerlKey>>() {
//                    @Override
//                    public void onSuccess(Response<PageResultForJob<ChannerlKey>> response) {
//                        mNetChannels = response.body().getData();
//                        initChannelData();
//                        initChannelFragments();
//                        setListener();
//                    }
//
//                    @Override
//                    public void onError(Response<PageResultForJob<ChannerlKey>> response) {
//                        super.onError(response);
//                        ToastUtil.toast(response.message());
//                    }
//
//                    @Override
//                    public void onCacheSuccess(Response<PageResultForJob<ChannerlKey>> response) {
//                        super.onCacheSuccess(response);
//                        mNetChannels = response.body().getData();
//                        initChannelData();
//                        initChannelFragments();
//                        setListener();
//                    }
//                });

//        RequestUtil.cachePrior(ZClient.getService(SportService.class).getSearchKeys(), new ZCallback<ZResponse<List<ChannerlKey>>>("CHANNER1key") {
        RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getSearchKeys(), new ZCallback<ZResponse<List<ChannerlKey>>>("CHANNER1key") {
            @Override
            protected void onSuccess(ZResponse<List<ChannerlKey>> zResponse) {
                mNetChannels = zResponse.getData();
                if(!isCache){
                    initChannelData();
                    initChannelFragments();
                    setListener();
                }
            }

            @Override
            protected void onCacheSuccess(ZResponse<List<ChannerlKey>> zResponse) {
                super.onCacheSuccess(zResponse);
                mNetChannels = zResponse.getData();
                if(null != mNetChannels){
                    initChannelData();
                    initChannelFragments();
                    setListener();
                    isCache = true;
                }

            }
        });

//        ZClient.getService(SportService.class).getSearchKeys().enqueue(new ZCallback<ZResponse<List<ChannerlKey>>>() {
//            @Override
//            public void onSuccess(ZResponse<List<ChannerlKey>> data) {
//                mNetChannels = data.getData();
//                initChannelData();
//                initChannelFragments();
//                setListener();
//            }
//
//            @Override
//            public void onFailure(Call<ZResponse<List<ChannerlKey>>> call, Throwable t) {
//                super.onFailure(call, t);
//                initChannelData();
//                initChannelFragments();
//                setListener();
//            }
//        });
    }

    private void setListener() {

        binding.ivOperation.setOnClickListener(this);

        mChannelPagerAdapter = new ChannelPagerAdapter(mChannelFragments, mSelectedChannels,getChildFragmentManager());
        mChannelPagerAdapter.notifyDataSetChanged();
        binding.vpContent.setAdapter(mChannelPagerAdapter);
        binding.vpContent.setOffscreenPageLimit(mSelectedChannels.size());

        binding.tabChannel.setTabPaddingLeftAndRight(UIUtils.dip2Px(10), UIUtils.dip2Px(10));
        binding.tabChannel.setupWithViewPager(binding.vpContent);
        binding.tabChannel.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) binding.tabChannel.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + binding.ivOperation.getMeasuredWidth());
            }
        });
        //隐藏指示器
        binding.tabChannel.setSelectedTabIndicatorHeight(0);

        binding.vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当页签切换的时候，如果有播放视频，则释放资源
//                JCVideoPlayer.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void initListener() {
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:

                break;
            case R.id.iv_operation:
                ChannelDialogFragment dialogFragment = ChannelDialogFragment.newInstance(mSelectedChannels, mUnSelectedChannels);
                dialogFragment.setOnChannelListener(this);
                dialogFragment.show(getChildFragmentManager(), "CHANNEL");
                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        mChannelPagerAdapter.notifyDataSetChanged();
                        binding.vpContent.setOffscreenPageLimit(mSelectedChannels.size());
                        binding.tabChannel.setCurrentItem(binding.tabChannel.getSelectedTabPosition());
                        ViewGroup slidingTabStrip = (ViewGroup) binding.tabChannel.getChildAt(0);
                        //注意：因为最开始设置了最小宽度，所以重新测量宽度的时候一定要先将最小宽度设置为0
                        slidingTabStrip.setMinimumWidth(0);
                        slidingTabStrip.measure(0, 0);
                        slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + binding.ivOperation.getMeasuredWidth());

                        //保存选中和未选中的channel
                        PreUtils.putString(Constant.SELECTED_CHANNEL_JSON, mGson.toJson(mSelectedChannels));
                        PreUtils.putString(Constant.UNSELECTED_CHANNEL_JSON, mGson.toJson(mUnSelectedChannels));
                    }
                });
                break;
        }

    }


    /**
     * 初始化已选频道和未选频道的数据
     */
    private void initChannelData() {
        String selectedChannelJson = PreUtils.getString(Constant.SELECTED_CHANNEL_JSON, "");
        String unselectChannel = PreUtils.getString(Constant.UNSELECTED_CHANNEL_JSON, "");
        if (TextUtils.isEmpty(selectedChannelJson) || TextUtils.isEmpty(unselectChannel)) {
//        if (TextUtils.isEmpty(selectedChannelJson)) {
            //本地没有title
//            String[] channels = getResources().getStringArray(R.array.channel);
//            String[] channelCodes = getResources().getStringArray(R.array.channel_code);
            //默认添加了全部频道
            for (int i = 0; i < mNetChannels.size(); i++) {
                String title = mNetChannels.get(i).getName();
                String code = mNetChannels.get(i).getId()+"";
                String pic = mNetChannels.get(i).getPic();
                mSelectedChannels.add(new Channel(title, code,pic));
            }

            selectedChannelJson = mGson.toJson(mSelectedChannels);//将集合转换成json字符串
//            KLog.i("selectedChannelJson:" + selectedChannelJson);
            PreUtils.putString(Constant.SELECTED_CHANNEL_JSON, selectedChannelJson);//保存到sp
        } else {
            //之前添加过
            List<Channel> selectedChannel = mGson.fromJson(selectedChannelJson, new TypeToken<List<Channel>>() {
            }.getType());
            List<Channel> unselectedChannel = mGson.fromJson(unselectChannel, new TypeToken<List<Channel>>() {
            }.getType());
            mSelectedChannels.clear();
            mUnSelectedChannels.clear();
            mSelectedChannels.addAll(selectedChannel);
            if(null != unselectChannel){
                mUnSelectedChannels.addAll(unselectedChannel);
            }
        }
    }

    /**
     * 初始化已选频道的fragment的集合
     */
    private void initChannelFragments() {
//        KLog.e("initChannelFragments");
//        mChannelCodes = m;
//        for (Channel channel : mSelectedChannels) {
        for (int i=0; i<mSelectedChannels.size();i++) {
            if(0==i){
                ChannelHomeFristFragment newsFragment = new ChannelHomeFristFragment();
                Bundle bundle = new Bundle();
//            bundle.putString(Constant.CHANNEL_CODE, channel.id);
                bundle.putString(Constant.CHANNEL_CODE, mSelectedChannels.get(i).id);
//            bundle.putBoolean(Constant.IS_VIDEO_LIST, channel.id.equals(mChannelCodes[1]));//是否是视频列表页面,根据判断频道号是否是视频
                newsFragment.setArguments(bundle);
                mChannelFragments.add(newsFragment);//添加到集合中
            }else{
                ChannelHomeFragment newsFragment  = new ChannelHomeFragment();
                Bundle bundle = new Bundle();
//            bundle.putString(Constant.CHANNEL_CODE, channel.id);
                bundle.putString(Constant.CHANNEL_CODE, mSelectedChannels.get(i).id);
//            bundle.putBoolean(Constant.IS_VIDEO_LIST, channel.id.equals(mChannelCodes[1]));//是否是视频列表页面,根据判断频道号是否是视频
                newsFragment.setArguments(bundle);
                mChannelFragments.add(newsFragment);//添加到集合中
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemMove(int starPos, int endPos) {
        listMove(mSelectedChannels, starPos, endPos);
        listMove(mChannelFragments, starPos, endPos);
    }


    @Override
    public void onMoveToMyChannel(int starPos, int endPos) {
        //移动到我的频道
        Channel channel = mUnSelectedChannels.remove(starPos);
        mSelectedChannels.add(endPos, channel);

        mChannelPagerAdapter.notifyDataSetChanged();

        ChannelHomeFragment newsFragment = new ChannelHomeFragment();
//        newsFragment.setReqKey();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.CHANNEL_CODE, channel.id);
//        bundle.putBoolean(Constant.IS_VIDEO_LIST, channel.id.equals(mChannelCodes[1]));
        newsFragment.setArguments(bundle);
        mChannelFragments.add(newsFragment);
        mChannelPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMoveToOtherChannel(int starPos, int endPos) {
        //移动到推荐频道
        mUnSelectedChannels.add(endPos, mSelectedChannels.remove(starPos));
        mChannelFragments.remove(starPos);
        mChannelPagerAdapter.notifyDataSetChanged();
    }

    private void listMove(List datas, int starPos, int endPos) {
        Object o = datas.get(starPos);
        //先删除之前的位置
        datas.remove(starPos);
        //添加到现在的位置
        datas.add(endPos, o);
    }

}
