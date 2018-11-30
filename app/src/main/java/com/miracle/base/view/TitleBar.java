package com.miracle.base.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.util.DisplayUtil;
import com.miracle.databinding.TitleBarBinding;


/**
 * Created by Administrator on 2018/2/28.
 */

public class TitleBar extends LinearLayout {
    public TitleBarBinding binding;
    private Context context;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(final Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.title_bar, this, true);
        initAttrs(attrs);
        binding.rlLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }


    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.TitleBar_TitleBar_LeftVisibility:
                    binding.rlLeft.setVisibility(ta.getInt(attr, VISIBLE));
                    break;
                case R.styleable.TitleBar_TitleBar_Title:
                    binding.tvTitle.setText(ta.getString(attr));
                    break;
                case R.styleable.TitleBar_TitleBar_TextColor:
                    binding.tvTitle.setTextColor(ta.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.TitleBar_TitleBar_TextSize:
                    binding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimension(attr, DisplayUtil.sp2px(context, 12)));
                    break;
                case R.styleable.TitleBar_TitleBar_Background:
                    binding.titleBarRoot.setBackground(ta.getDrawable(attr));
                    break;
                case R.styleable.TitleBar_TitleBar_BackgroundResource:
                    binding.titleBarRoot.setBackgroundResource(ta.getResourceId(attr, 0));
                    break;
                case R.styleable.TitleBar_TitleBar_BackgroundColor:
                    binding.titleBarRoot.setBackgroundColor(ta.getColor(attr, Color.WHITE));
                    break;

                case R.styleable.TitleBar_TitleBar_LeftText:
                    binding.tvLeft.setText(ta.getString(attr));
                    break;
                case R.styleable.TitleBar_TitleBar_RightText:
                    binding.tvRight.setText(ta.getString(attr));
                    break;
                case R.styleable.TitleBar_TitleBar_RightBackground:
                    binding.ivRight.setBackground(ta.getDrawable(attr));
                    binding.ivRight.setVisibility(VISIBLE);
                    binding.tvRight.setVisibility(GONE);
                    break;
                case R.styleable.TitleBar_TitleBar_RightTextColor:
                    binding.tvRight.setTextColor(ta.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.TitleBar_TitleBar_RightTextSize:
                    binding.tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimension(attr, DisplayUtil.sp2px(context, 12)));
                    break;
            }
        }
        ta.recycle();
    }

    public void showRight(boolean show) {
        binding.rlRight.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    public void showLeft(boolean show) {
        binding.rlLeft.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    public void setTitle(String title, int textColor) {
        binding.tvTitle.setText(title);
        binding.tvTitle.setTextColor(textColor);
    }

    public void setTitle(String title, int textColor, int textSize) {
        binding.tvTitle.setText(title);
        binding.tvTitle.setTextColor(textColor);
        binding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(context, textSize));
    }

    public void setRight(String text) {
        binding.tvRight.setText(text);
    }

    public void setRight(String text, int textColor) {
        binding.tvRight.setText(text);
        binding.tvRight.setTextColor(textColor);
    }

    public void setRight(String text, int textColor, int textSize) {
        binding.tvRight.setText(text);
        binding.tvRight.setTextColor(textColor);
        binding.tvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(context, textSize));
    }


    public void setLeft(int textColor, int textSize) {
        binding.tvLeft.setTextColor(textColor);
        binding.tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(context, textSize));
    }

    public void setLeft(String text, int textColor, int textSize) {
        binding.tvLeft.setText(text);
        binding.tvLeft.setTextColor(textColor);
        binding.tvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.sp2px(context, textSize));
    }

    public void setLeftColor(int textColor) {
        binding.tvLeft.setTextColor(textColor);
    }

    public void setRightClickListener(OnClickListener listener) {
        binding.rlRight.setOnClickListener(listener);
    }

    public void setLeftClickListener(OnClickListener listener) {
        binding.rlLeft.setOnClickListener(listener);
    }

}
