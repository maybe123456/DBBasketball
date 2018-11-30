package com.miracle.base.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.miracle.base.App;


/**
 *
 * Created by Administrator on 2018/3/2.
 */

public class IconFontTextView extends AppCompatTextView {
    public IconFontTextView(Context context) {
        this(context, null);
    }

    public IconFontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.getApp().getTypeFace());
    }
}
