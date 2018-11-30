package com.miracle.base.view.zchatview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.databinding.ZChatviewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NaOH on 2018/7/17 10:19 (星期二).
 */
public class ZChatView extends LinearLayout implements ZValueBar.OnRangeSettledListener {
    private ZChatviewBinding binding;
    private ZAdapter mAdapter;
    private List<KData> mData = new ArrayList<>();
    public static final int PRECISION = 10000;

    public ZChatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.z_chatview, this, true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        binding.recyclerView.setAdapter(mAdapter = new ZAdapter());
        binding.recyclerView.setFocusable(false);

        binding.zValueBar.setOnRangeSettledListener(this);
    }

    public void setData(List<KData> data) {
        if (data != null && !data.isEmpty()) {
            binding.zValueBar.initValue(data);
            mData = data;
        }
    }


    @Override
    public void onRangeSettled(double max, double min, double perHeight) {
        mAdapter.setPerHeight(max, min, perHeight, mData);
        binding.recyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
    }
}
