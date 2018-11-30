package com.miracle.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.util.DisplayUtil;
import com.miracle.databinding.BaseItemItiBinding;


/**
 * Created by Administrator on 2017/6/6.
 * ITI:ImageView  TextView ImageView
 */

public class BaseItem_ITI extends LinearLayout {
    private final BaseItemItiBinding binding;
    private Context context;

    public BaseItem_ITI(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.base_item_iti, this, true);
        initAttrs(attrs);

    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseItem_ITI);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.BaseItem_ITI_text:
                    binding.tvContent.setText(ta.getString(attr));
                    break;
                case R.styleable.BaseItem_ITI_iti_leftDrawable:
                    binding.ivLeft.setImageResource(ta.getResourceId(attr, R.mipmap.star));
                    break;
                case R.styleable.BaseItem_ITI_BaseItem_ITI_TextColor:
                    binding.tvContent.setTextColor(ta.getColor(attr, ContextCompat.getColor(context, R.color.main_text_color)));
                    break;
                case R.styleable.BaseItem_ITI_BaseItem_ITI_TextSize:
                    binding.tvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimension(attr, DisplayUtil.dip2px(context, 12)));
                    break;
                case R.styleable.BaseItem_ITI_BaseItem_ITI_RightArrowVisibility:
                    binding.ivRight.setVisibility(ta.getInt(attr, VISIBLE));
                    break;

            }

        }
        ta.recycle();
    }
}
