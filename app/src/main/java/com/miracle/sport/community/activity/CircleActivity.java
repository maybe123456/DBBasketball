package com.miracle.sport.community.activity;

import android.view.View;
import android.widget.AdapterView;

import com.miracle.R;
import com.miracle.base.BaseActivity;
import com.miracle.base.network.ZCallback;
import com.miracle.base.network.ZClient;
import com.miracle.base.network.ZResponse;
import com.miracle.databinding.ActivityCircleBinding;
import com.miracle.sport.SportService;
import com.miracle.sport.community.adapter.CircleIndexAdapter;
import com.miracle.sport.community.bean.CircleBean;
import com.miracle.sport.community.fragment.CircleFragment;

import java.util.List;

/**
 * Created by Michael on 2018/10/30 15:38 (星期二)
 */
public class CircleActivity extends BaseActivity<ActivityCircleBinding> {

    private CircleIndexAdapter mAdapter;
    private CircleFragment circleFragment;

    private boolean isFromPublishPostActivity;

    @Override
    public int getLayout() {
        return R.layout.activity_circle;
    }

    @Override
    public void initView() {
        setTitle("圈子");
        isFromPublishPostActivity = getIntent().getBooleanExtra("isFromPublishPostActivity", false);
        if (!isFromPublishPostActivity) {
            setRight("完成");
            setRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        mAdapter = new CircleIndexAdapter(mContext);
        binding.indexListView.setAdapter(mAdapter);
        circleFragment = (CircleFragment) getSupportFragmentManager().findFragmentById(R.id.circleFragment);
        circleFragment.setBoolean(isFromPublishPostActivity);
    }

    private int mPosition;


    @Override
    public void initListener() {
        binding.indexListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long itemId) {
                mAdapter.setSelectPosition(position);
                circleFragment.setData(mAdapter.getItem(position).getChild());
                mPosition = position;
            }
        });
    }

    @Override
    public void loadData() {
        ZClient.getService(SportService.class).getCircleList().enqueue(new ZCallback<ZResponse<List<CircleBean>>>(this) {
            @Override
            public void onSuccess(ZResponse<List<CircleBean>> data) {
                if (data != null && data.getData() != null && !data.getData().isEmpty()) {
                    mAdapter.update(data.getData());
                    mAdapter.setSelectPosition(mPosition);
                    circleFragment.setData(mAdapter.getItem(mPosition).getChild());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
