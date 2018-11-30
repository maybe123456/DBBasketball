package com.miracle.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.util.DisplayUtil;
import com.miracle.databinding.ItemBarBinding;


/**
 * Created by Administrator on 2018/3/2.
 */

public class ItemBar extends LinearLayout {

    private final ItemBarBinding binding;
    private Context context;

    public ItemBar(Context context) {
        this(context, null);
    }

    public ItemBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_bar, this, true);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ItemBar);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.ItemBar_ItemBar_LeftVisibility:
                    binding.tvIconLeft.setVisibility(ta.getInt(attr, VISIBLE));
                    break;
                case R.styleable.ItemBar_ItemBar_RightVisibility:
                    binding.tvIconRight.setVisibility(ta.getInt(attr, INVISIBLE));
                    break;
                case R.styleable.ItemBar_ItemBar_Text:
                    binding.tvText.setText(ta.getString(attr));
                    break;
                case R.styleable.ItemBar_ItemBar_Left_Icon:
                    binding.tvIconLeft.setText(ta.getString(attr));
                    break;
                case R.styleable.ItemBar_ItemBar_Right_Icon:
                    binding.tvIconRight.setText(ta.getString(attr));
                    break;
                case R.styleable.ItemBar_ItemBar_TextColor:
                    binding.tvText.setTextColor(ta.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.ItemBar_ItemBar_TextSize:
                    binding.tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimension(attr, DisplayUtil.sp2px(context, 12)));
                    break;
                case R.styleable.ItemBar_ItemBar_Left_TextSize:
                    binding.tvIconLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimension(attr, DisplayUtil.sp2px(context, 12)));
                    break;
                case R.styleable.ItemBar_ItemBar_Background:
                    binding.itemBarRoot.setBackgroundColor(ta.getColor(attr, Color.WHITE));
                    break;

                case R.styleable.ItemBar_ItemBar_Left_TextColor:
                    binding.tvIconLeft.setTextColor(ta.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.ItemBar_ItemBar_Right_TextColor:
                    binding.tvIconRight.setTextColor(ta.getColor(attr, Color.BLACK));
                    break;
            }
        }
        ta.recycle();
    }

    public void setText(String text) {
        binding.tvText.setText(text);
    }

    public void setLeftIcon(String icon) {
        binding.tvIconLeft.setText(icon);
    }

    public void setRightVisibility(int visibility) {
        binding.tvIconRight.setVisibility(visibility);
    }

}
