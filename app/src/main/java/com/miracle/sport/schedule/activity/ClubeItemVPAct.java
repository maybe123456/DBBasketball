package com.miracle.sport.schedule.activity;

import android.os.Message;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.sport.schedule.fragment.FragClubeItemVP;

//某赛事的列表
public class ClubeItemVPAct extends BaseActivity {
    public static String EXTRA_ID = "EXTRA_ID";
    public static String EXTRA_NAME = "EXTRA_NAME";
    int reqId = -1;
    String reqName = "";

    @Override
    public int getLayout() {
        return R.layout.clube_item_act;
    }

    @Override
    public void initView() {
        if(getIntent() != null){
            reqId = getIntent().getIntExtra(EXTRA_ID,-1);
            reqName = getIntent().getExtras().getString(EXTRA_NAME);
        }
        setTitle(reqName);
        FragClubeItemVP frag_clube_itemvp_frag = (FragClubeItemVP) getSupportFragmentManager().findFragmentById(R.id.frag_clube_itemvp_frag);
        Message msg = new Message();
        msg.what = FragClubeItemVP.MSG_REQDATA_WAHT;
        msg.arg1 = reqId;
        msg.obj = reqName;
        frag_clube_itemvp_frag.uiHandler.sendMessage(msg);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View v) {

    }
}
