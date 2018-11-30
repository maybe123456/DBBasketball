package com.miracle.base.view.zchatview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.databinding.ZValuebarBinding;

import java.util.List;


/**
 * Created by NaOH on 2018/7/17 10:26 (星期二).
 */
public class ZValueBar extends LinearLayout {
    private ZValuebarBinding binding;
    double max = 0;
    double min = Double.MAX_VALUE;
    private int divisor;
    private int bodyHeight;

    public ZValueBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.z_valuebar, this, true);


        binding.tvIndexBody.setOnSizeChangedListener(new ZTextView.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int width, int height) {
                listener.onRangeSettled(max, min, ((double) height) / divisor);
            }
        });
    }


    public void initValue(List<KData> data) {
        for (KData kData : data) {
            double kmax = kData.getMax();
            double kmin = kData.getMin();
            if (kmax > max) {
                max = kmax;
            }
            if (kmin < min) {
                min = kmin;
            }
        }
        binding.tvMax.setText(((int) max) + "");
        binding.tvIndexBody.setText(((int) min) + "");

        divisor = (int) (max * ZChatView.PRECISION - min * ZChatView.PRECISION);
    }

    private OnRangeSettledListener listener;

    public void setOnRangeSettledListener(OnRangeSettledListener listener) {
        this.listener = listener;
    }


    public interface OnRangeSettledListener {
        void onRangeSettled(double max, double min, double perHeight);
    }
}
