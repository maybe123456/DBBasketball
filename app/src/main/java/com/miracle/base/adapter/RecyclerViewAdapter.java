package com.miracle.base.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

public abstract class RecyclerViewAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public RecyclerViewAdapter(int layoutResId) {
        super(layoutResId);
    }
}
