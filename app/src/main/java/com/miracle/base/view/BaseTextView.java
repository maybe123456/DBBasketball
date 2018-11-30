package com.miracle.base.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.miracle.R;
import com.miracle.base.util.DisplayUtil;


/**
 * Created by Administrator on 2017/7/5.
 */

public class BaseTextView extends AppCompatTextView {
    public BaseTextView(Context context) {
        this(context, null);
    }

    public BaseTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextColor(getResources().getColor(R.color.main_text_color));
        setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(context, 14));
        setMovementMethod(ScrollingMovementMethod.getInstance());
    }


}
