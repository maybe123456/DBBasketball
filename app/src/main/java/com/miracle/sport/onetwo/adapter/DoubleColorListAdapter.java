package com.miracle.sport.onetwo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.miracle.sport.onetwo.netbean.LotteryCatListItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleColorListAdapter extends RecyclerViewAdapter<LotteryCatListItem> {

    Context mContext;
    DisplayMetrics displayMetrics;
    LayoutInflater layoutInflater;

    public DoubleColorListAdapter(Context mContext) {
        super(R.layout.item_double_color_detail);
        this.mContext = mContext;
        displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    protected void convert(BaseViewHolder helper, LotteryCatListItem item) {
        //双色球
//        helper.setText(R.id.tvLdName, item.getName());
        helper.setText(R.id.tvLdTime, item.getOpen_time());
//        helper.setText(R.id.tvLdNum, item.getNumber());

        String[] hostNumArr = item.getHost_num().split(" ");
        String[] firstNumArr = item.getFirst_num().split(" ");

        RecyclerView redrv = (RecyclerView) helper.getView(R.id.item_red_rv);
        RecyclerView bluerv = (RecyclerView) helper.getView(R.id.item_blue_rv);

        if (!(hostNumArr.length == 1 && hostNumArr[0].equals("0")))
            setBall(redrv, hostNumArr, R.drawable.red_ball);
        else
            redrv.setVisibility(View.GONE);

        if (!(firstNumArr.length == 1 && firstNumArr[0].equals("0")))
            setBall(bluerv, firstNumArr, R.drawable.blue_ball);
        else
            bluerv.setVisibility(View.GONE);
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
