package com.miracle.sport.onetwo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.sport.onetwo.entity.CpHall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CPHallListAdapter extends RecyclerViewAdapter<CpHall> {

    Context mContext;
    DisplayMetrics displayMetrics;
    LayoutInflater layoutInflater;

    public CPHallListAdapter(Context mContext) {
        super(R.layout.item_lottery_detail);
        this.mContext = mContext;
        displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected void convert(BaseViewHolder helper, CpHall item) {
        //双色球
        helper.setText(R.id.tvLdName, item.getName());
        helper.setText(R.id.tvLdTime, item.getOpen_time());
        helper.setText(R.id.tvLdNum, item.getPeriod());

        if(item.getRed_num() != null){
            setBallNumber(item.getRed_num(),R.drawable.red_ball,(RecyclerView) helper.getView(R.id.item_red_rv));
        }else{
            setBallNumber(item.getHost_num(),R.drawable.red_ball,(RecyclerView) helper.getView(R.id.item_red_rv));
        }

        if(item.getBlue_num() != null){
            setBallNumber(item.getBlue_num(),R.drawable.blue_ball,(RecyclerView) helper.getView(R.id.item_blue_rv));
        }else{
            setBallNumber(item.getFirst_num(),R.drawable.blue_ball,(RecyclerView) helper.getView(R.id.item_blue_rv));
        }
    }

    private void setBallNumber(String num, int drawableRes, RecyclerView redrv){
        String[] numArr = {};
        if(!TextUtils.isEmpty(num)){
            numArr = num.split(" ");
            Log.d(TAG, "setBallNumber() called with: num = [" + num + "] numArr.length： "+numArr.length+", drawableRes = [" + drawableRes + "], redrv = [" + redrv + "]");
            if(numArr.length != 0 && !(numArr.length == 1 && numArr[0].equals("0")))
            {
                setBall(redrv, numArr, drawableRes);
                return;
            }
        }
        redrv.setVisibility(View.GONE);
    }

    private void setBall(RecyclerView rv, String[] numbers, int bgColorRes) {
        LotteryCategoryListBallAdapter redBallAdapter;
        if (rv.getAdapter() == null)
            redBallAdapter = new LotteryCategoryListBallAdapter(mContext);
        else
            redBallAdapter = (LotteryCategoryListBallAdapter) rv.getAdapter();
        redBallAdapter.setColor(bgColorRes);
        redBallAdapter.setNewData(Arrays.asList(numbers));
        rv.setAdapter(redBallAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(lm);
        rv.setHorizontalScrollBarEnabled(false);
        rv.setVisibility(View.VISIBLE);
    }

    private void ballViewHolder(String[] number, LinearLayout llhostNum, int colorRes) {
        List<View> redTextViewList;
        if (null == llhostNum.getTag()) {
            redTextViewList = new ArrayList<>();
            llhostNum.setTag(redTextViewList);
        } else {
            redTextViewList = (List<View>) llhostNum.getTag();
        }

        int i = 0;
        for (; i < number.length; i++) {
            TextView textView;
            if (redTextViewList.size() <= i) {
                View item = layoutInflater.inflate(R.layout.item_red_ball, null);
                textView = (TextView) item.findViewById(R.id.tv_ball);
                item.setTag(textView);
                textView.setBackgroundColor(colorRes);
                if (textView.getLayoutParams() != null)
                    Log.i(TAG, "ballViewHolder: XXXX " + textView.getLayoutParams().width + " h:" + textView.getLayoutParams().height);
                redTextViewList.add(item);
                llhostNum.addView(item);
            } else {
                View item = redTextViewList.get(i);
                textView = (TextView) item.getTag();
            }
            textView.setVisibility(View.VISIBLE);
            textView.setText("" + number[i]);
        }
        if (i < redTextViewList.size()) {
            while (i < redTextViewList.size()) {
                redTextViewList.get(i).setVisibility(View.GONE);
            }
        }
    }


}
