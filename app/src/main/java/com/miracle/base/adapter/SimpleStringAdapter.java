package com.miracle.base.adapter;

import com.chad.library.adapter.base.BaseViewHolder;

public class SimpleStringAdapter extends RecyclerViewAdapter<String> {
    public SimpleStringAdapter() {
        super(android.R.layout.simple_list_item_1);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(android.R.id.text1, item);
    }
}
