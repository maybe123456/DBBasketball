package com.miracle.sport.onetwo.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;

public class LotteryCategoryListBallAdapter extends RecyclerViewAdapter<String> {

    Context mContext;
    int bgColorRes = R.drawable.red_ball;

    public LotteryCategoryListBallAdapter(Context mContext) {
        super(R.layout.item_red_ball);
        this.mContext = mContext;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_ball,item);
        helper.setBackgroundRes(R.id.tv_ball, bgColorRes);
    }

    public void setColor(int bgColorRes){
        this.bgColorRes = bgColorRes;
    }

}
