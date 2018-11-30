package com.miracle.base.view.zchatview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by NaOH on 2018/7/27 17:21 (星期五).
 */
public class ZTextView extends AppCompatTextView {
    public ZTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        listener.onSizeChanged(getWidth(), getHeight());
    }

    private OnSizeChangedListener listener;

    public void setOnSizeChangedListener(OnSizeChangedListener listener) {
        this.listener = listener;
    }

    public interface OnSizeChangedListener {
        void onSizeChanged(int width, int height);
    }
}
