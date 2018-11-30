package com.miracle.sport.onetwo.frag;

import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.miracle.R;
import com.miracle.base.network.RequestUtil;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZPageLoadCallback;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.ContextHolder;
import com.miracle.databinding.FragmentCategoryDetailBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.home.activity.SimpleWebCommentActivity;
import com.miracle.sport.home.adapter.HomeListAdapter;
import com.miracle.sport.home.bean.FishListItem;
import com.miracle.sport.home.bean.Football;
import com.miracle.sport.onetwo.inter.CallBackListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * Created by Administrator on 2018/3/5.
 *
 * 列表 1
 */

public class FragCpItemList extends HandleFragment<FragmentCategoryDetailBinding> implements BaseQuickAdapter.OnItemClickListener {
//    public CpListItemAdapter mAdapter;
    public static final int MSG_WHAT_KEY_REQKEY = 1;

    public String reqKey = "1";
    public ZPageLoadCallback callBack;

    public boolean showBanner = false;
    private com.youth.banner.Banner banner;
    public HomeListAdapter mAdapter;

    public CallBackListener callBackListener;
    public CallBackListener getCallBackListener() {
        return callBackListener;
    }

    public FragCpItemList() {
        super();
    }

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_category_detail;
    }

    @Override
    public void initView() {
        Log.i("TAG", "initView: xxxxxxxxxxx 2");
        mAdapter = new HomeListAdapter(mContext);
        mAdapter.addHeaderView(initBanner());
        mAdapter.setOnItemClickListener(this);

        binding.recyclerView.setAdapter(mAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        DividerItemDecoration div = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        div.setDrawable(getResources().getDrawable(R.drawable.recycle_divier_shape));
        binding.recyclerView.addItemDecoration(div);
        binding.recyclerView.setHasFixedSize(true);
        //FootNewsPostActivity
//        binding.tvCategoryTitle.setText(R.string.main_title_1);

        setShowBanner(showBanner);
        initCallback();
//        loadData();
    }

    public boolean isShowBanner() {
        return showBanner;
    }

    public void setShowBanner(boolean showBanner) {
        this.showBanner = showBanner;
        if(banner != null)
            banner.setVisibility(showBanner ? View.VISIBLE : View.GONE);
    }

    public String getReqKey() {
        return reqKey;
    }

    private void initCallback() {
        callBack = new ZPageLoadCallback<ZResponse<List<Football>>>(mAdapter,binding.recyclerView) {
            @Override
            public void requestAction(int page, int pageSize) {
                RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getNewsSpotrList(Integer.parseInt(reqKey), page, pageSize),callBack);
            }

            @Override
            protected void onSuccess(ZResponse<List<Football>> data) {
                super.onSuccess(data);
            }
        };

        callBack = new ZPageLoadCallback<ZResponse<List<Football>>>(mAdapter, binding.recyclerView, this) {
            @Override
            public void requestAction(int page, int pageSize) {
//                ZClient.getService(CPServer.class).cpList(page, pageSize, "cp", reqKey).enqueue(this);
//                if(reqKey.equals("1")) {//只有首页展示的频道做缓存
//                    callBack.setCachKey("homepage_fcil_key");
//                }
//                else {
//                    callBack.setCachKey(null);
//                }
                callBack.setCachKey("homepage_fcil_key" + reqKey+page);
                RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getNewsSpotrList(Integer.parseInt(reqKey), page, pageSize),callBack);
//                RequestUtil.cacheUpdate(ZClient.getService(SportService.class).getNewsSpotrList(Integer.parseInt(reqKey), page, pageSize),callBack);
                if(callBackListener != null)
                    callBackListener.onStart();
            }

            @Override
            public void onFinish(Call call) {
                super.onFinish(call);
                if (mAdapter.getData() != null && mAdapter.getData().size() > 0)
                    setUIStatus(ShowStat.NORMAL);
                else
                    setUIStatus(ShowStat.NODATA);

                if(callBackListener != null)
                    callBackListener.onFinish(mAdapter.getData());
            }
        };
        callBack.initSwipeRefreshLayout(binding.swipeRefreshLayout);
    }

    private View initBanner() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.banner_layout, null);
        banner = header.findViewById(R.id.banner);
        ArrayList images = new ArrayList<>();
        images.add(R.mipmap.b3);
        images.add(R.mipmap.b5);
        banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(ContextHolder.getContext()).load(path).into(imageView);
            }
        });
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        banner.setDelayTime(1000 * 3);
        banner.start();
        return header;
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {

    }

    public void setReqKey(String reqKey) {
        if(!this.reqKey.equals(reqKey) && mAdapter != null) {
            mAdapter.getData().clear();
            mAdapter.notifyDataSetChanged();
        }
        this.reqKey = reqKey;
        if(callBack != null)
            callBack.onRefresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (position > adapter.getData().size())
            return;

        Object o = adapter.getData().get(position);
        if (o instanceof Football) {
            Football pd = (Football) o;
            if (pd.getId() != 0) {
//                Intent pdAct = new Intent(getActivity(), FootNewsPostActivity.class);
//                pdAct.putExtra(FootNewsPostActivity.EXTRA_KEY_POSTID, pd.getId());
//                startActivity(pdAct);
                Intent pdAct = new Intent(getActivity(), SimpleWebCommentActivity.class);
                pdAct.putExtra("id", pd.getId());
                startActivity(pdAct);
            }
        }
    }

    @Override
    public void loadData() {
        callBack.onRefresh();
    }

    @Override
    public void onHandleMessage(Message msg) {
        if(msg.what == MSG_WHAT_KEY_REQKEY){
            setReqKey(msg.arg1+"");
        }
    }
}
