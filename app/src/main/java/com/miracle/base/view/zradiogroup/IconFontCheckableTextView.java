package com.miracle.base.view.zradiogroup;

import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

import com.miracle.base.App;


public class IconFontCheckableTextView extends AppCompatRadioButton {

    public IconFontCheckableTextView(Context context) {
        this(context, null);
    }

    public IconFontCheckableTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFontCheckableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(App.getApp().getTypeFace());
    }

}