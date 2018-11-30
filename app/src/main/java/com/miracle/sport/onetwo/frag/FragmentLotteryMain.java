package com.miracle.sport.onetwo.frag;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.miracle.R;
import com.miracle.base.AppConfig;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.switcher.GameActivity;
import com.miracle.base.util.ContextHolder;
import com.miracle.databinding.FragmentCpMainBinding;
import com.miracle.databinding.FragmentCpMainTopBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.home.bean.ChannerlKey;
import com.miracle.sport.home.view.XRecyclerView;
import com.miracle.sport.onetwo.act.OneFragActivity;
import com.miracle.sport.onetwo.netbean.LotteryCatListItem;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//FragmentCpMainBinding
//binding = R.layout.fragment_cp_main
//binding.list_frag = subFrag

//topBinding = R.layout.fragment_cp_main_top
//subFrag.mAdapter.addHeaderView(topBinding)
public class FragmentLotteryMain extends HandleFragment<FragmentCpMainBinding>{
    public static String TAG = "TAG";
    public static int WHAT_GET_MARQUEE = 1;
    private static int WHAT_FLIP_TEXT = 10;

    FragmentCpMainTopBinding topBinding;
    FragCpItemList subFrag;
    Banner banner;

    TextSwitcher textSwitcher;
    List<Spanned> mardatas = new ArrayList<Spanned>();
    int mardIndex = 0;

    int[] rcwl = new int[2];
    Rect mrc = new Rect();
    View marquee_ll1;
    View marquee_ll1_float;
    TextSwitcher cp_main_top_ts_float;
    RecyclerView.LayoutManager layoutManager;

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
                marquee_ll1 = topBinding.marqueeLl1;
                marquee_ll1_float = binding.getRoot().findViewById(R.id.marquee_ll1_float);
                cp_main_top_ts_float = marquee_ll1_float.findViewById(R.id.cp_main_top_ts_float);
                cp_main_top_ts_float.setFactory(getFactory());
                cp_main_top_ts_float.setInAnimation(getContext(),android.R.anim.slide_in_left);
                cp_main_top_ts_float.setOutAnimation(getContext(),android.R.anim.slide_out_right);
                layoutManager = subFrag.binding.recyclerView.getLayoutManager();

                marquee_ll1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        subFrag.binding.recyclerView.getLocationInWindow(rcwl);
                        Log.i(TAG, "onGlobalLayout: rcwl " + Arrays.toString(rcwl));
                        marquee_ll1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
                Log.i(TAG, "onViewCreated: rcwl " + Arrays.toString(rcwl));
                subFrag.binding.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if(marquee_ll1 == null)
                            return;

                        marquee_ll1.getGlobalVisibleRect(mrc);
                        Log.i(TAG, " mrc.top - " + mrc.top  + " rcwl[1]  " + rcwl[1]  + " = "  +(mrc.top - rcwl[1]));

                        if(mrc.top - rcwl[1] <= 0 || layoutManager.getPosition(layoutManager.getChildAt(0)) > 0){
                            marquee_ll1_float.setVisibility(View.VISIBLE);
                        }else{
                            marquee_ll1_float.setVisibility(View.GONE);
                        }
                    }
                });

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
                LinearLayout main_frag_hs_ll3 = topBinding.getRoot().findViewById(R.id.main_frag_hs_ll3);
                main_frag_hs_ll.removeAllViews();
                mardatas.clear();
                int i = 0;
                for(ChannerlKey item : zResponse.getData()){
                    //排除 ‘推荐’
                    if(1 == Integer.parseInt(item.getId()))
                        continue;
                    if(i % 2 == 0)
                        addToHS(item.getName(),Integer.parseInt(item.getId()),item.getPic(), main_frag_hs_ll);
                    else
                        addToHS(item.getName(),Integer.parseInt(item.getId()),item.getPic(), main_frag_hs_ll3);
                    mardatas.add(Html.fromHtml("<font color=\"#cc0000\">"+item.getName()+"</font>已增加消息"));
                    i++;
                }

            }
        };

        zCallback.setCachKey("homepage_fishtype");
        RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getSearchKeys(), zCallback);
    }

    private void addToHS(final String str, final int key, String picUrl,LinearLayout ll){
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
        ll.addView(view);
    }

    private void initMard(List<Spanned> list) {
//        mardatas.add(Html.fromHtml("<font color=\"#cc0000\">分类列表</font>"));
        mardatas.addAll(list);

        textSwitcher = topBinding.getRoot().findViewById(R.id.cp_main_top_ts);
        textSwitcher.setFactory(getFactory());
        textSwitcher.setInAnimation(getContext(),android.R.anim.slide_in_left);
        textSwitcher.setOutAnimation(getContext(),android.R.anim.slide_out_right);
        uiHandler.sendEmptyMessage(WHAT_FLIP_TEXT);
    }

    @NonNull
    private ViewSwitcher.ViewFactory getFactory() {
        return new ViewSwitcher.ViewFactory() {
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
        };
    }

    private void initBanner() {
        banner = topBinding.mainFarg1Banner;
        ArrayList images = new ArrayList<>();
//        images.add(R.mipmap.banner_f2);
//        images.add(R.mipmap.b3);
//        images.add(R.mipmap.b5);
//        images.add(R.mipmap.banner04);
        images.add("file:///android_asset/lottery/01.png");
        images.add("file:///android_asset/lottery/02.png");
        images.add("file:///android_asset/lottery/03.png");
        images.add("file:///android_asset/lottery/04.png");
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
                if(mardatas.size() != 0) {
                    mardIndex = mardIndex % mardatas.size();
                    Spanned text = mardatas.get(mardIndex);
                    mardIndex++;
                    textSwitcher.setText(text);
                    if(cp_main_top_ts_float != null){
                        Log.e("TAG", "onHandleMessage: XXXXX cp_main_top_ts_float : " +cp_main_top_ts_float);
                        cp_main_top_ts_float.setText(text);
                    }
                }
                uiHandler.removeMessages(WHAT_FLIP_TEXT);
                uiHandler.sendEmptyMessageDelayed(WHAT_FLIP_TEXT, 2000);
            }
        }
    }
}
