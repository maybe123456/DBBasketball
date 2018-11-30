package com.miracle.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.miracle.R;
import com.miracle.base.util.DisplayUtil;
import com.miracle.base.adapter.ZTextWatcher;
import com.miracle.databinding.XEditTextBinding;


/**
 * Created by Administrator on 2017/8/21.
 */

public class XEditText extends LinearLayout {

    private final XEditTextBinding binding;

    public XEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.x_edit_text, this, true);
        initAttrs(context, attrs);
        setListener();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.XEditText);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int attr = ta.getIndex(i);
            switch (attr) {
                case R.styleable.XEditText_XEditText_DrawableLeft:
                    Drawable drawableLeft = ta.getDrawable(attr);
                    if (drawableLeft != null) {
                        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                        binding.et.setCompoundDrawables(drawableLeft, null, null, null);
                    }
                    break;

                case R.styleable.XEditText_XEditText_Hint:
                    binding.et.setHint(ta.getString(attr));
                    break;
                case R.styleable.XEditText_XEditText_Text:
                    binding.et.setText(ta.getString(attr));
                    break;
                case R.styleable.XEditText_XEditText_MaxLength:
                    int maxlength = ta.getInt(attr, -1);
                    binding.et.setFilters(maxlength >= 0 ? new InputFilter[]{new InputFilter.LengthFilter(maxlength)} : new InputFilter[0]);
                    break;
                case R.styleable.XEditText_XEditText_IsPassword:
                    binding.et.setInputType(ta.getBoolean(attr, false) ? InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    break;
                case R.styleable.XEditText_XEditText_ShowEyes:
                    binding.cbRight.setVisibility(ta.getBoolean(attr, false) ? VISIBLE : GONE);
                    break;
                case R.styleable.XEditText_XEditText_InputType:
                    binding.et.setInputType(ta.getInt(attr, EditorInfo.TYPE_NULL));
                    break;
                case R.styleable.XEditText_XEditText_TextColor:
                    binding.et.setTextColor(ta.getColor(attr, ContextCompat.getColor(context, R.color.main_text_color)));
                    break;
                case R.styleable.XEditText_XEditText_TextColorHint:
                    binding.et.setHintTextColor(ta.getColor(attr, ContextCompat.getColor(context, R.color.main_hint_color)));
                    break;
                case R.styleable.XEditText_XEditText_PaddingLeft:
                    binding.et.setPadding((int) ta.getDimension(attr, DisplayUtil.dip2px(context, 12)), 0, 0, 0);
                    break;
                case R.styleable.XEditText_XEditText_PaddingRight:
                    binding.et.setPadding(0, 0, (int) ta.getDimension(attr, DisplayUtil.dip2px(context, 12)), 0);
                    break;

                case R.styleable.XEditText_XEditText_DrawablePadding:
                    binding.et.setCompoundDrawablePadding(ta.getDimensionPixelSize(attr, 10));
                    break;
                case R.styleable.XEditText_XEditText_TextSize:
                    binding.et.setTextSize(TypedValue.COMPLEX_UNIT_PX, ta.getDimension(attr, DisplayUtil.dip2px(context, 12)));
                    break;
            }
        }

        ta.recycle();
    }

    private void setListener() {
        binding.ivX.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.et.setText("");
                binding.ivX.setVisibility(GONE);
            }
        });

        binding.et.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.ivX.setVisibility(binding.et.getText().length() > 0 ? VISIBLE : GONE);
                } else {
                    binding.ivX.setVisibility(GONE);
                }
            }
        });
        binding.et.addTextChangedListener(new ZTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                binding.ivX.setVisibility(s.length() == 0 ? GONE : VISIBLE);
            }
        });

        binding.cbRight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                binding.et.setInputType(isChecked ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                binding.et.setSelection(binding.et.getText().length());
            }
        });
    }


    public String getText() {
        return binding.et.getText().toString();
    }

    public void setText(String s) {
        binding.et.setText(s);
    }

    public void setHint(String hint) {
        binding.et.setHint(hint);
    }

    public void setInputType(int type) {
        binding.et.setInputType(type);
    }


    public void setReadOnly() {
        binding.et.setEnabled(false);
        binding.ivX.setVisibility(GONE);
    }

    public boolean isEmpty() {
        return TextUtils.isEmpty(binding.et.getText());
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        binding.et.addTextChangedListener(textWatcher);
    }
}
