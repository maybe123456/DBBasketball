package com.miracle.sport.onetwo.act;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.ChannelActivityBinding;
import com.miracle.sport.onetwo.util.channel.ChannelAdapter;
import com.miracle.sport.onetwo.netbean.CPServer;
import com.miracle.sport.onetwo.netbean.CpTitleItem;
import com.miracle.sport.onetwo.operation.CollectionUtil;
import com.miracle.sport.onetwo.util.channel.ChannelEntity;
import com.miracle.sport.onetwo.util.channel.helper.ItemDragHelperCallback;
import com.miracle.sport.onetwo.util.channel.helper.OnEditChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 频道 增删改查 排序
 * Created by YoKeyword on 15/12/29.
 *
 */
public class ChannelSelectActivity extends BaseActivity<ChannelActivityBinding> {

    private RecyclerView mRecy;
    ChannelAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.channel_activity;
    }

    @Override
    public void initView() {
        setTitle("选择频道");
        mRecy = (RecyclerView) findViewById(R.id.recy);
        init();
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void loadData() {
        reqData();
    }

    private void init() {
        final List<ChannelEntity> items = new ArrayList<>();
        final List<ChannelEntity> otherItems = new ArrayList<>();

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        mRecy.setLayoutManager(manager);

        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecy);

        adapter = new ChannelAdapter(this, helper, items, otherItems);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = adapter.getItemViewType(position);
                return viewType == ChannelAdapter.TYPE_MY || viewType == ChannelAdapter.TYPE_OTHER ? 1 : 4;
            }
        });
        adapter.setOnEditChangeListener(new OnEditChangeListener() {
            @Override
            public void onEditChange(boolean isBeginEdit) {
                if(isBeginEdit)
                    return;
                List<CpTitleItem> list = new ArrayList<>();
                for(ChannelEntity ce : adapter.getmMyChannelItems()){
                    CpTitleItem cpTitleItem = new CpTitleItem();
                    cpTitleItem.setLOCAL_isCollected(true);
                    cpTitleItem.setName(ce.getName());
                    cpTitleItem.setType(ce.getType());
                    list.add(cpTitleItem);
                }
                for(ChannelEntity ce : adapter.getmOtherChannelItems()){
                    CpTitleItem cpTitleItem = new CpTitleItem();
                    cpTitleItem.setLOCAL_isCollected(false);
                    cpTitleItem.setName(ce.getName());
                    cpTitleItem.setType(ce.getType());
                    list.add(cpTitleItem);
                }
                CollectionUtil.meargData(list,null,null,true);
            }
        });
        mRecy.setAdapter(adapter);

        //todo 修改 move 监听器
//        mRecy.setOnGenericMotionListener();

//        adapter.setOnMyChannelItemClickListener(new CpTitleListChannelAdapter.OnMyChannelItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Toast.makeText(ChannelSelectActivity.this, items.get(position).getName(), Toast.LENGTH_SHORT).show();
//            }
//        });
        reqData();
    }

    private void reqData() {
        ZClient.getService(CPServer.class).cpTitleList().enqueue(new ZCallback<ZResponse<List<CpTitleItem>>>(this) {
            @Override
            public void onSuccess(ZResponse<List<CpTitleItem>> data) {
                //数据规整及保存
                List<CpTitleItem> mcList = new ArrayList<>();
                List<CpTitleItem> moList = new ArrayList<>();

                CollectionUtil.meargData(data.getData(), mcList, moList);

                //转换数据
                List<ChannelEntity> mcceList = new ArrayList<>();
                List<ChannelEntity> occeList = new ArrayList<>();
                for (CpTitleItem item : mcList) {
                    mcceList.add(new ChannelEntity(0, item.getName(), item.getType()));
                }
                for (CpTitleItem item : moList) {
                    occeList.add(new ChannelEntity(0, item.getName(), item.getType()));
                }

                //填充ui
                adapter.getmMyChannelItems().clear();
                adapter.getmOtherChannelItems().clear();
                adapter.setmMyChannelItems(mcceList);
                adapter.setmOtherChannelItems(occeList);

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
