package com.miracle.michael.doudizhu.fragment;

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
import com.miracle.databinding.F2DdzBinding;
import com.miracle.michael.doudizhu.adapter.DDZIndexAdapter;
import com.miracle.michael.doudizhu.bean.DDZIndexBean;

import java.util.List;


public class DDZF2 extends BaseFragment<F2DdzBinding> {
    private DDZIndexAdapter indexAdapter;

    private DDZF2Child detailFragment;


    @Override
    public int getLayout() {
        return R.layout.f2_ddz;
    }

    @Override
    public void initView() {
        binding.titleBar.showLeft(drawerLayout != null);
        indexAdapter = new DDZIndexAdapter(mContext);
        binding.indexListView.setAdapter(indexAdapter);
        detailFragment = (DDZF2Child) getActivity().getSupportFragmentManager().findFragmentById(R.id.categoryDetailFragment);
        reqData();
    }

    private void reqData() {
        ZClient.getService(ZService.class).getDDZNewsTypeList().enqueue(new ZCallback<ZResponse<List<DDZIndexBean>>>() {
            @Override
            public void onSuccess(ZResponse<List<DDZIndexBean>> data) {
                List<DDZIndexBean> keys = data.getData();
                indexAdapter.update(keys.subList(1, keys.size()));
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
                detailFragment.setReqKey(indexAdapter.getItem(position).getClass_id());
            }
        });

    }


    @Override
    public void onClick(View view) {

    }

    private DrawerLayout drawerLayout;

    public DDZF2 setDrawer(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
        return this;
    }
}
