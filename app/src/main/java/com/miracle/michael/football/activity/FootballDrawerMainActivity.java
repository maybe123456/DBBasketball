package com.miracle.michael.football.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.GOTO;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.bean.UserInfoBean;
import com.miracle.base.network.GlideApp;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.base.util.CommonUtils;
import com.miracle.databinding.ActivityFootballDrawerBinding;
import com.miracle.michael.common.bean.DrawerItemBean;
import com.miracle.michael.football.fragment.FootballF1;
import com.miracle.michael.football.fragment.FootballF3;

import java.util.Arrays;

/**
 * Created by Michael on 2018/10/18 19:52 (星期四)
 */
public class FootballDrawerMainActivity extends BaseActivity<ActivityFootballDrawerBinding> {
    private UserInfoBean userInfo;
    private DrawerItemAdapter mAdapter;
    private FragmentManager fragmentManager;
    private Fragment fragment1, fragment2, fragment3, fragment4;

    private DrawerItemBean[] drawerItems = {new DrawerItemBean(CommonUtils.getString(R.string.icon_tab_home), "快讯"),
            new DrawerItemBean(CommonUtils.getString(R.string.icon_tab_auction), "数据排行榜"),
            new DrawerItemBean(CommonUtils.getString(R.string.icon_chatroom), "聊天室"),
            new DrawerItemBean(CommonUtils.getString(R.string.icon_order_manage), "赛事分析"),
            new DrawerItemBean(CommonUtils.getString(R.string.icon_settings), "设置")
    };

    @Override
    public int getLayout() {
        return R.layout.activity_football_drawer;
    }

    @Override
    public void initView() {
        hideTitle();
        binding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        });

        binding.recyclerView.setAdapter(mAdapter = new DrawerItemAdapter());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        mAdapter.setNewData(Arrays.asList(drawerItems));
        fragmentManager = getSupportFragmentManager();
        fragment1 = new FootballF1().setDrawer(binding.drawerLayout);
//        fragment2 = new FootballF5().setDrawer(binding.drawerLayout);
        fragment3 = new FootballF3().setDrawer(binding.drawerLayout);
//        fragment4 = new FootballF4().setDrawer(binding.drawerLayout);
        fragmentManager.beginTransaction().replace(R.id.container, fragment1).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CommonUtils.getUser() != null)
            reqData();
    }

    private void reqData() {
        ZClient.getService(ZService.class).getUserInfo().enqueue(new ZCallback<ZResponse<UserInfoBean>>() {
            @Override
            public void onSuccess(ZResponse<UserInfoBean> data) {
                userInfo = data.getData();
                binding.tvName.setText(userInfo.getNickname());
                GlideApp.with(mContext).load(userInfo.getImg()).placeholder(R.mipmap.default_head).into(binding.ivHeadImg);
            }
        });
    }

    private final class DrawerItemAdapter extends RecyclerViewAdapter<DrawerItemBean> {

        public DrawerItemAdapter() {
            super(R.layout.item_drawer);
        }

        @Override
        protected void convert(BaseViewHolder helper, DrawerItemBean item) {
            helper.setText(R.id.tvIconLeft, item.getIcon());
            helper.setText(R.id.tvText, item.getName());
        }

    }

    @Override
    public void initListener() {
        binding.llMe.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position) {
                    case 0:
                        fragmentManager.beginTransaction().replace(R.id.container, fragment1).commit();
                        break;
                    case 1:
                        fragmentManager.beginTransaction().replace(R.id.container, fragment3).commit();
                        break;
                    case 2:
                        GOTO.ChatActivity(mContext);
                        break;
                    case 3:
                        GOTO.FootballSaiShiFenXiActivity(mContext);
                        break;
                    case 4:
                        GOTO.SettingActivity(mContext);
                        break;

                }
                binding.drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llMe:
                if (userInfo == null) {
                    GOTO.LoginActivity(mContext);
                } else {
                    GOTO.FootballMeActivity(mContext,userInfo);
                }
                break;
        }
    }

}
