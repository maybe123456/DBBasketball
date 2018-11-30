package com.nightonke.boommenu.BoomButtons;

import android.content.Context;
import android.view.Gravity;

public class CustomBitTextCButton extends TextInsideCircleButton {
    private CustomBitTextCButton(Builder builder, Context context) {
        super(builder, context);

//        image.setVisibility(View.GONE);
        inner_tv.setGravity(Gravity.CENTER);
    }

}
