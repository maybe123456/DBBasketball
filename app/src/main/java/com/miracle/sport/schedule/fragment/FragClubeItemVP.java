package com.miracle.sport.schedule.fragment;

import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.miracle.R;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.FragClubeitmeVpBinding;
import com.miracle.sport.schedule.adapter.ClubeItemTabAdapter;
import com.miracle.sport.schedule.bean.ClubeItem;
import com.miracle.sport.schedule.bean.ClubeType;
import com.miracle.sport.schedule.net.FootClubServer;

import java.util.List;

import retrofit2.Call;

//赛事 tab
public class FragClubeItemVP extends HandleFragment<FragClubeitmeVpBinding> {
    public static final int MSG_REQDATA_WAHT = 1;

    int reqId;
    String name;
    FragmentManager supportFm;
    ClubeItemTabAdapter tabAdapter;
    ClubeType parentType;

    @Override
    public int getLayout() {
        return R.layout.frag_clubeitme_vp;
    }

    @Override
    public void initView() {
        supportFm = getActivity().getSupportFragmentManager();
        binding.tablayout1.setupWithViewPager(binding.viewpager1);

        tabAdapter = new ClubeItemTabAdapter(supportFm);
        tabAdapter.setParentType(parentType);
        binding.viewpager1.setAdapter(tabAdapter);
    }

    public void reqData(){
        ZClient.getService(FootClubServer.class).getFootClubItems(reqId).enqueue(new ZCallback<ZResponse<List<ClubeItem>>>() {
            @Override
            public void onSuccess(ZResponse<List<ClubeItem>> data) {
                tabAdapter.getDatas().clear();
                if(data.getData() == null || data.getData().size() == 0)
                {
                    setUIStatus(ShowStat.NODATA);
                }else{
                    for(ClubeItem item : data.getData())
                    {
                        tabAdapter.getDatas().add(item);
                    }
                    setUIStatus(ShowStat.NORMAL);
                }
                tabAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ZResponse<List<ClubeItem>>> call, Throwable t) {
                setUIStatus(ShowStat.ERR);
                super.onFailure(call, t);
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
    public void onHandleMessage(Message msg) {
        switch (msg.what){
            case MSG_REQDATA_WAHT:
                reqId = msg.arg1;
                name = (String) msg.obj;
                parentType = new ClubeType(reqId, name);
                tabAdapter.setParentType(parentType);
                reqData();
                break;
        }
    }
}
