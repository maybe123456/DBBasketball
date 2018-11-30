package com.miracle.base.view.zchatview;

import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.base.util.CommonUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by NaOH on 2018/7/17 16:56 (星期二).
 */
public class ZAdapter extends RecyclerViewAdapter<KData> {
    private final int colorFall;
    private final int colorRise;
    private SimpleDateFormat format;
    private double mMax;
    private double mMin;
    private double perHeight;


    public ZAdapter() {
        super(R.layout.k_item);
        format = new SimpleDateFormat("dd/MM", Locale.CHINA);
        colorFall = CommonUtils.getColor(R.color.k_fall);
        colorRise = CommonUtils.getColor(R.color.k_rise);
    }

    @Override
    protected void convert(BaseViewHolder helper, KData item) {
        if (perHeight == 0) return;
        LinearLayout body = helper.getView(R.id.llBody);
        int position = mData.indexOf(item);
        if (position == 0) {
            helper.setBackgroundColor(R.id.bodyView, colorRise);
        } else {
            helper.setBackgroundColor(R.id.bodyView, item.getMax() >= getItem(position - 1).getMax() ? colorRise : colorFall);
        }
        helper.setText(R.id.tvMax, NumberUtil.numberFmt(item.getMax(), "#"));
        helper.setText(R.id.tvTime, item.getDate() == 0 ? "" : format.format(item.getDate()));
        double max = item.getMax() * ZChatView.PRECISION;
        double min = item.getMin() * ZChatView.PRECISION;

        int topMargin = (int) ((mMax - max) * perHeight);
        int bottomMargin = (int) ((min - mMin) * perHeight);
        CommonUtils.setMargins(body, 0, topMargin, 0, bottomMargin);
    }


    public void setPerHeight(double max, double min, double perHeight, List<KData> data) {
        mMax = max * ZChatView.PRECISION;
        mMin = min * ZChatView.PRECISION;
        this.perHeight = perHeight;
        mData.addAll(data);
        int itemCount = mData.size();
        if (itemCount / 8 == 0) {
            List<KData> bottomData = new ArrayList<>();
            for (int i = 0; i < 8 - itemCount; i++) {
                bottomData.add(new KData());
            }
            mData.addAll(bottomData);
        }
        notifyDataSetChanged();
    }
}
