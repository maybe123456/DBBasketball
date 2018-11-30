package com.miracle.base.view.zradiogroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.util.DisplayUtil;
import com.miracle.databinding.ZRadioButtonBinding;


public class ZRadioButton extends LinearLayout implements Checkable {
    private boolean mChecked;
    private int mPosition;
    private ZRadioButtonBinding binding;
    private boolean focusZoom;
    private View mIndicator;
    private boolean showIndicator;
    public static final int INDICATOR_TOP = 0;
    public static final int INDICATOR_BOTTOM = 1;

    public ZRadioButton(Context context) {
        this(context, null);
    }

    public ZRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.z_radio_button, this, true);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.ZRadioButton));
        setClickable(true);
    }

    private void initAttrs(TypedArray ta) {
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.ZRadioButton_ZRadioButton_IndicatorColor:
                    binding.indicatorTop.setBackgroundColor(ta.getColor(attr, Color.BLACK));
                    binding.indicatorBottom.setBackgroundColor(ta.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.ZRadioButton_ZRadioButton_FocusZoom:
                    focusZoom = ta.getBoolean(attr, false);
                    break;
                case R.styleable.ZRadioButton_ZRadioButton_Icon:
                    binding.tvIcon.setVisibility(VISIBLE);
                    binding.tvIcon.setText(ta.getString(attr));
                    break;
                case R.styleable.ZRadioButton_ZRadioButton_Text:
                    binding.tvText.setText(ta.getString(attr));
                    break;
                case R.styleable.ZRadioButton_ZRadioButton_TextSize:
                    binding.tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimension(attr, DisplayUtil.sp2px(getContext(), 12)));
                    break;
                case R.styleable.ZRadioButton_ZRadioButton_IconTextSize:
                    float dimension = ta.getDimension(attr, DisplayUtil.sp2px(getContext(), 12));
                    binding.tvIcon.setTextSize(TypedValue.COMPLEX_UNIT_PX, dimension);
                    break;
                case R.styleable.ZRadioButton_ZRadioButton_TextColor:
                    binding.tvIcon.setTextColor(ta.getColorStateList(attr));
                    binding.tvText.setTextColor(ta.getColorStateList(attr));
                    break;
                case R.styleable.ZRadioButton_ZRadioButton_indicator_position:
                    showIndicator = true;
                    if (ta.getInt(attr, 0) == INDICATOR_TOP) {
                        binding.indicatorBottom.setVisibility(INVISIBLE);
                        mIndicator = binding.indicatorTop;
                    } else {
                        binding.indicatorTop.setVisibility(INVISIBLE);
                        mIndicator = binding.indicatorBottom;
                    }
                    break;
            }
        }
        ta.recycle();
    }

    @Override
    public boolean performClick() {
        if (!mChecked) {
            toggle();
        }
        return super.performClick();

    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked ^ checked) {
            mChecked ^= true;
            if (showIndicator) {
                mIndicator.setVisibility(mChecked ? VISIBLE : INVISIBLE);
            }
            binding.tvText.setChecked(mChecked);
            if (focusZoom) {
                float size = binding.tvText.getTextSize();
                float zoom = DisplayUtil.sp2px(getContext(), 2);
                binding.tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX, mChecked ? size + zoom : size - zoom);
            }

            binding.tvIcon.setChecked(mChecked);
            if (listener != null)
                listener.onCheckedChange(this, mPosition, mChecked);
        }
    }

    public ZRadioButton setText(String text) {
        binding.tvText.setText(text);
        return this;
    }

    /**
     * @param position should use ZRadioButton.INDICATOR_TOP or ZRadioButton.INDICATOR_BOTTOM
     */
    public void setIndicatorPosition(int position) {
        showIndicator = true;
        if (position == INDICATOR_TOP) {
            binding.indicatorBottom.setVisibility(INVISIBLE);
            mIndicator = binding.indicatorTop;
        } else {
            binding.indicatorTop.setVisibility(INVISIBLE);
            mIndicator = binding.indicatorBottom;
        }
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }

    private OnCheckedChangeListener listener;

    public void setOncheckedChangeListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(ZRadioButton zRadioButton, int position, boolean isChecked);
    }

    public void setfocusZoom(boolean focusZoom) {
        this.focusZoom = focusZoom;
    }
}
