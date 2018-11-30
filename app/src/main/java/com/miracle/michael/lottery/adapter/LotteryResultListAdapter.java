package com.miracle.michael.lottery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.michael.lottery.bean.LotteryResultBean;

public class LotteryResultListAdapter extends RecyclerViewAdapter<LotteryResultBean> {
    private Context context;
    private LinearLayout.LayoutParams params;

    public LotteryResultListAdapter(Context context) {
        super(R.layout.item_lottery_result);
        this.context = context;
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 15, 0);
    }


    @Override
    protected void convert(BaseViewHolder helper, LotteryResultBean item) {
        helper.setText(R.id.tvNumber, item.getNumber());
        helper.setText(R.id.tvOpenTime, item.getOpen_time());
        setHostNum(helper, item.getFirst_num(), R.id.llFirstNum, R.drawable.shape_oval_blueball);
        setHostNum(helper, item.getHost_num(), R.id.llHostNum, R.drawable.shape_oval_redball);
        helper.setGone(R.id.llFirstNumP, !"0".equals(item.getFirst_num()));

    }


    private void setHostNum(BaseViewHolder helper, String hostNum, int viewId, int resId) {
        LinearLayout llHostNum = helper.getView(viewId);
        llHostNum.removeAllViews();
        String[] nums = hostNum.split(" ");
        for (String s : nums) {
            TextView textView = new TextView(context);
            textView.setBackgroundResource(resId);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setWidth(60);
            textView.setHeight(60);
            textView.setGravity(Gravity.CENTER);
            textView.setText(s);
            textView.setLayoutParams(params);
            llHostNum.addView(textView);
        }
    }
}
