//package com.miracle.sport.schedule.fragment;
//
//import android.os.Message;
//import android.view.View;
//
//import com.miracle.R;
//import com.miracle.base.network.PageLoadCallback;
//import com.miracle.base.network.ZClient;
//import com.miracle.databinding.FragClubetypeListBinding;
//import com.miracle.sport.schedule.adapter.ClubePostSSAdapter;
//import com.miracle.sport.schedule.bean.ClubeItem;
//import com.miracle.sport.schedule.bean.ClubeType;
//import com.miracle.sport.schedule.net.FootClubServer;
//
//public class FragClubeItemList extends HandleFragment<FragClubetypeListBinding> {
//    ClubeItem req;
//    ClubeType parentType;
//    PageLoadCallback callback;
//    ClubePostSSAdapter adapter;
//
//    public ClubeType getParentType() {
//        return parentType;
//    }
//
//    public void setParentType(ClubeType parentType) {
//        this.parentType = parentType;
//    }
//
//    public ClubeItem getReq() {
//        return req;
//    }
//
//    public void setReq(ClubeItem req) {
//        this.req = req;
//    }
//
//    @Override
//    public void onHandleMessage(Message msg) {
//        if(msg.what == 1)
//            callback.onRefresh();
//    }
//
//    @Override
//    public int getLayout() {
//        return R.layout.frag_clubetype_list;
//    }
//
//    @Override
//    public void initView() {
//
//        callback = new PageLoadCallback() {
//            @Override
//            public void requestAction(int page, int limit) {
//                ZClient.getService(FootClubServer.class).getFootClubItems(req)
//            }
//        };
//    }
//
//    public void setReq(){
//
//    }
//
//    @Override
//    public void initListener() {
//
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
//}
