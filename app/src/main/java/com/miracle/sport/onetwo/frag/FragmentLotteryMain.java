package com.miracle.sport.onetwo.frag;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.gongwen.marqueen.SimpleMF;
import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.im.util.Utils;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.switcher.GameActivity;
import com.miracle.base.util.ContextHolder;
import com.miracle.base.util.DisplayUtil;
import com.miracle.databinding.FragmentCpMainTopBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.home.bean.ChannerlKey;
import com.miracle.sport.onetwo.act.OneFragActivity;
import com.miracle.sport.onetwo.netbean.FSServer;
import com.miracle.sport.onetwo.netbean.FishType;
import com.miracle.sport.onetwo.netbean.LotteryCatListItem;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

//FragmentCpMainBinding
public class FragmentLotteryMain extends HandleFragment<FragmentCpMainTopBinding>{
    public static int WHAT_GET_MARQUEE = 1;
    private static int WHAT_FLIP_TEXT = 10;

    FragmentCpMainTopBinding topBinding;
    FragCpItemList subFrag;
    Banner banner;

    TextSwitcher textSwitcher;
    List<Spanned> mardatas = new ArrayList<Spanned>();
    int mardIndex = 0;

    @Override
    public int getLayout() {
        return R.layout.fragment_cp_main;
    }

    @Override
    public void initView() {
        Log.i("TAG", "initView: xxxxxxxxxxx 1");
        setShowTitle(true);
        setTitle("精选");
        getTitleBar().showLeft(false);
        topBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.fragment_cp_main_top, null, false);
        initTopHeader();

        subFrag = addSubFragment(R.id.list_frag, FragCpItemList.class, new AddSubFragListener<FragCpItemList>() {
            @Override
            public void onFindFromTag(FragCpItemList fragCpItemList) {
                subFrag = fragCpItemList;
                setupSubFrag();
            }

            @Override
            public void onNewInstance(FragCpItemList fragCpItemList) {
                subFrag = fragCpItemList;
            }

            @Override
            public void onCommit() {
                setupSubFrag();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        uiHandler.sendEmptyMessageDelayed(WHAT_FLIP_TEXT, 2000);
    }

    public void setupSubFrag(){
        subFrag.setFragLifeListner(new FragLifeListner() {
            @Override
            public void onViewCreated() {
                subFrag.mAdapter.addHeaderView(topBinding.getRoot());
                subFrag.mAdapter.notifyDataSetChanged();
                subFrag.binding.getRoot().requestLayout();
                subFrag.loadData();
                LoadDataHSuserBarType();
            }

            @Override
            public void onDestoryView() {

            }
        });
    }

    private void initTopHeader() {
        initBanner();
        initMard(new ArrayList());
        initTicket();
        initButtons();

        //公告数据
//        BussnisUtil.getAllNewNumber(uiHandler ,WHAT_GET_MARQUEE);
//        ZClient.getService(FSServer.class)

    }

    private void initButtons() {
        topBinding.getRoot().findViewById(R.id.main_farg_tryrand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),OneFragActivity.class);
                intent.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, RandomNumFragment.class);
                startActivity(intent);
            }
        });

        topBinding.getRoot().findViewById(R.id.main_frag_more3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OneFragActivity.class);
                intent.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, Fragment1cp.class);
                startActivity(intent);
            }
        });

        topBinding.getRoot().findViewById(R.id.main_frag_more2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OneFragActivity.class);
                intent.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, Fragment2cpChannelPager.class);
                startActivity(intent);
            }
        });
    }

    private void initTicket() {
        topBinding.mainLl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OneFragActivity.class);
                intent.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, CpHallFragment.class);
                startActivity(intent);
            }
        });
        topBinding.mainLl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OneFragActivity.class);
                intent.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, LotteryChartFragment.class);
                startActivity(intent);
            }
        });
    }
    private void LoadDataHSuserBarType(){
//        RequestUtil.cachePrior(ZClient.getService(SportService.class).getSearchKeys(), new ZCallback<ZResponse<List<ChannerlKey>>>("CHANNER1key") {
//            @Override
//            protected void onSuccess(ZResponse<List<ChannerlKey>> zResponse) {
//                mNetChannels = zResponse.getData();
//                initChannelData();
//                initChannelFragments();
//                setListener();
//            }
//        });

        ZCallback zCallback = new ZCallback<ZResponse<List<ChannerlKey>>>(subFrag.binding.swipeRefreshLayout){
            @Override
            protected void onSuccess(ZResponse<List<ChannerlKey>> zResponse) {
                LinearLayout main_frag_hs_ll = topBinding.getRoot().findViewById(R.id.main_frag_hs_ll);
                main_frag_hs_ll.removeAllViews();
                for(ChannerlKey item : zResponse.getData()){
                    //排除 ‘推荐’
                    if(1 != Integer.parseInt(item.getId()))
                        addToHS(item.getName(),Integer.parseInt(item.getId()),item.getPic());
                }
            }
        };

        zCallback.setCachKey("homepage_fishtype");
        RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getSearchKeys(), zCallback);
    }

    private void addToHS(final String str, final int key, String picUrl){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.main_frag_hs1_item,null);
        ImageView iv = view.findViewById(R.id.main_farg_hs1_iv);
        ((TextView)view.findViewById(R.id.main_farg_hs1_tv1)).setText(str);
        GlideApp.with(mContext).load(picUrl).into(iv);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开分类文章列表
                Intent i = new Intent(getActivity(), OneFragActivity.class);
                i.putExtra(OneFragActivity.EXTRA_KEY_FRAG_CLASS, FragCpItemList.class);
                Message msg = new Message();
                msg.what = FragCpItemList.MSG_WHAT_KEY_REQKEY;
                msg.arg1 = key;
                i.putExtra(OneFragActivity.EXTRA_KEY_MSG, msg);
                i.putExtra(OneFragActivity.EXTRA_KEY_ACT_TITLE, ""+str);
                startActivity(i);
            }
        });
        LinearLayout main_frag_hs_ll = topBinding.getRoot().findViewById(R.id.main_frag_hs_ll);
        main_frag_hs_ll.addView(view);
    }

    private void initMard(List<Spanned> list) {
        mardatas.add(Html.fromHtml("<font color=\"#cc0000\">鞋包服饰</font>已更新"));
        mardatas.add(Html.fromHtml("<font color=\"#cc0000\">文体车品</font>已更新"));
        mardatas.add(Html.fromHtml("<font color=\"#cc0000\">数码家电</font>已更新"));
        mardatas.addAll(list);

        textSwitcher = topBinding.getRoot().findViewById(R.id.cp_main_top_ts);
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView t = new TextView(getContext());
                t.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                t.setTextColor(Color.BLACK);
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.LEFT;
                t.setLayoutParams(layoutParams);
                return t;
            }
        });
        textSwitcher.setInAnimation(getContext(),android.R.anim.slide_in_left);
        textSwitcher.setOutAnimation(getContext(),android.R.anim.slide_out_right);
        uiHandler.sendEmptyMessage(WHAT_FLIP_TEXT);
    }

    private void initBanner() {
        banner = topBinding.mainFarg1Banner;
        ArrayList images = new ArrayList<>();
//        images.add(R.mipmap.banner_f2);
//        images.add(R.mipmap.b3);
//        images.add(R.mipmap.b5);
//        images.add(R.mipmap.banner04);
        images.add("file:///android_asset/lottery/banner01.png");
        images.add("file:///android_asset/lottery/banner02.png");
        images.add("file:///android_asset/lottery/banner03.png");
        banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(ContextHolder.getContext()).load(path).into(imageView);
            }
        });
        banner.setImages(images);
        banner.setDelayTime(1000 * 3);
        banner.start();
        banner.setOnBannerListener(new OnBannerListener() {
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

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onHandleMessage(Message msg) {
        if(msg.what == WHAT_GET_MARQUEE){
            if(msg.obj != null && msg.obj instanceof List)
            {
                List<LotteryCatListItem> list = (List<LotteryCatListItem>) msg.obj;
                List<Spanned> data = new ArrayList<>();
                for(LotteryCatListItem item : list){
                    String str = item.getName() + ": <font color=\"#ff0000\">" + item.getHost_num() + "</font> <font color=\"#0000ff\">" + item.getFirst_num()+"</font>";
                    data.add(Html.fromHtml(str));
                }
                mardatas.clear();
                initMard(data);
            }
        }else if(msg.what == WHAT_FLIP_TEXT){
            if(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.RESUMED))
            {
                Spanned text = mardatas.get(mardIndex);
                textSwitcher.setText(text);

                mardIndex++;
                mardIndex = mardIndex % mardatas.size();
                uiHandler.removeMessages(WHAT_FLIP_TEXT);
                uiHandler.sendEmptyMessageDelayed(WHAT_FLIP_TEXT, 2000);
            }
        }
    }
}
