package com.miracle.sport.onetwo.frag;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.util.ContextHolder;
import com.miracle.databinding.Fragment2vpBinding;
import com.miracle.sport.onetwo.act.ChannelSelectActivity;
import com.miracle.sport.onetwo.adapter.CpTitleListChannelAdapter;
import com.miracle.sport.onetwo.adapter.FootClubKeyIndexAdapter;
import com.miracle.sport.onetwo.netbean.CPServer;
import com.miracle.sport.onetwo.netbean.CpTitleItem;
import com.miracle.sport.onetwo.operation.CollectionUtil;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class Fragment2cpChannelPager extends BaseFragment<Fragment2vpBinding> {
    public static int CHOSE_TYPE = 1;
    public static String CHOSE_TYPE_BUNDLE_KEY = "CHOSE_TYPE";
    private String type;

    private FootClubKeyIndexAdapter indexAdapter;
//    private FragCpItemList detailFragment;
    CpTitleListChannelAdapter cfpAdapter;

    @Override
    public int getLayout() {
        return R.layout.fragment2vp;
    }

    @Override
    public void initView() {
        //indexAdapter = new FootClubKeyIndexAdapter(mContext);
//        //binding.indexListView.setAdapter(indexAdapter);
////        detailFragment = (FragCpItemList) getActivity().getSupportFragmentManager().findFragmentById(R.id.categoryDetailFragment);
        setTitle("彩票新闻");
        binding.searchEt1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_NEXT) {
//                    hideSoftKeyboard();  TODO
                    jumpSearchAct();
                }
                return false;
            }
        });

        binding.tablayout1.setupWithViewPager(binding.viewpager1);
        binding.tablayout1.setTabMode(TabLayout.MODE_FIXED);
        binding.tablayout1.setTabMode(TabLayout.MODE_SCROLLABLE);
        cfpAdapter = new CpTitleListChannelAdapter(getActivity().getSupportFragmentManager());
        binding.viewpager1.setAdapter(cfpAdapter);

        reqData();
    }


    @Override
    public void initListener() {
        binding.btnAddChannel.setOnClickListener(this);
        binding.searchTv.setOnClickListener(this);
    }

    private void goChoseActivity() {
        //搜索结果 activity TODO
//        Intent indexListAct = new Intent(mContext, FootballKeyIndexListActivity.class);
//        startActivityForResult(indexListAct, CHOSE_TYPE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (CHOSE_TYPE == requestCode && resultCode == RESULT_OK) {
//            type = data.getStringExtra(CHOSE_TYPE_BUNDLE_KEY);
//            detailFragment.setKey(type);
//            TypeTitle tt = FragCpItemList.getTypeTitleByType(type);
            //gallery1
            //GlideApp.with(mContext).load(tt.url)
//                    .placeholder(R.mipmap.defaule_img)
//                    .error(R.mipmap.empty)
//                    .into(binding.ivPoster);
        }
        Log.d("TAG", "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        reqData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_channel:
                openChannelSelectAct();
                break;
            case R.id.search_tv:
                jumpSearchAct();
                break;
        }

    }

    private void jumpSearchAct() {
//        if (!TextUtils.isEmpty(binding.searchEt1.getText().toString())) {
//            Intent searchAct = new Intent(getActivity(), FootNewsSearchActivity.class);
//            searchAct.putExtra(FootNewsSearchActivity.EXTRA_KEY_SEARCH_KEYWORD, binding.searchEt1.getText().toString());
//            startActivity(searchAct);
//        } else {
//            UiUtils.shack(getActivity(), binding.searchEt1);
//        }
    }

    private void openChannelSelectAct() {
        Intent intent = new Intent(mContext, ChannelSelectActivity.class);
        startActivityForResult(intent, CHOSE_TYPE);
    }

    private void initBanner() {
        ArrayList images = new ArrayList<>();
//        images.add(FragCpItemList.yc);
//        images.add(FragCpItemList.zc);
//        images.add(FragCpItemList.xj);
//        images.add(FragCpItemList.yj);
//        images.add(FragCpItemList.dj);
//        images.add(FragCpItemList.og);
        binding.banner.setImages(images).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(ContextHolder.getContext()).load(path).into(imageView);
            }
        });
        binding.banner.setDelayTime(Integer.MAX_VALUE);
        binding.banner.start();

        binding.banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                //ToastUtil.toast("fuck");
                goChoseActivity();
            }
        });
        binding.banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.i("TAG", "onPageSelected: " + i);
//                FootNewsKey fk = indexAdapter.getItem(i % indexAdapter.getCount());
//                TypeTitle tt = FragCpItemList.getTypeTitleByType(fk.getKey());
//
//                detailFragment.setKey(fk.getKey());
//                binding.ftitle.setTitle(tt.getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void reqData() {
//        setShowProgress(true);
        ZClient.getService(CPServer.class).cpTitleList().enqueue(new ZCallback<ZResponse<List<CpTitleItem>>>(this) {
            @Override
            public void onSuccess(ZResponse<List<CpTitleItem>> data) {
                List<CpTitleItem> outMcList = new ArrayList<>();
                List<CpTitleItem> outMoList = new ArrayList<>();
                CollectionUtil.meargData(data.getData(), outMcList, outMoList);
                cfpAdapter = new CpTitleListChannelAdapter(getActivity().getSupportFragmentManager());
                cfpAdapter.setData(outMcList);
                binding.viewpager1.setAdapter(cfpAdapter);
                cfpAdapter.notifyDataSetChanged();
            }

//            @Override
//            public void onFinish() {
//                super.onFinish();
////                setShowProgress(false);
//            }
        });
    }

    @Override
    public void loadData() {
        super.loadData();
        reqData();
    }
}
