package com.miracle.michael.football.fragment;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;

import com.miracle.R;
import com.miracle.base.BaseFragment;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.base.network.ZService;
import com.miracle.databinding.F2FootballBinding;
import com.miracle.michael.football.adapter.FootballIndexAdapter;
import com.miracle.michael.football.bean.FootballMatchIndexBean;

import java.util.List;


public class FootballF2 extends BaseFragment<F2FootballBinding> {
    private FootballIndexAdapter indexAdapter;

    private FootballF2Child detailFragment;


    @Override
    public int getLayout() {
        return R.layout.f2_football;
    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        indexAdapter = new FootballIndexAdapter(mContext);
        binding.indexListView.setAdapter(indexAdapter);
        detailFragment = (FootballF2Child) getActivity().getSupportFragmentManager().findFragmentById(R.id.categoryDetailFragment);
        reqData();
    }

    private void reqData() {
        ZClient.getService(ZService.class).getFootballMatchesIndex().enqueue(new ZCallback<ZResponse<List<FootballMatchIndexBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<FootballMatchIndexBean>> data) {
                List<FootballMatchIndexBean> keys = data.getData();
                indexAdapter.update(keys.subList(0, keys.size() - 2));
            }
        });
    }

    @Override
    public void initListener() {
        binding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null)
                    drawerLayout.openDrawer(Gravity.START);
            }
        });
        binding.indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long itemId) {
                indexAdapter.setSelectPosition(position);
                detailFragment.setReqKey(indexAdapter.getItem(position).getKey());
            }
        });

    }


    @Override
    public void onClick(View view) {

    }

    private DrawerLayout drawerLayout;

    public FootballF2 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
