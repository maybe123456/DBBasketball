package com.miracle.sport.onetwo.adapter;

import android.graphics.Color;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.miracle.R;
import com.miracle.base.adapter.RecyclerViewAdapter;
import com.miracle.sport.onetwo.entity.RandBallEntity;
import com.nightonke.boommenu.Util;

public class RandBallAdapter extends RecyclerViewAdapter<RandBallEntity> {

    int buttonDim;
    int normalColor;

    public int getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getButtonDim() {
        return buttonDim;
    }

    public void setButtonDim(int buttonDim) {
        this.buttonDim = buttonDim * 2;
    }

    public RandBallAdapter() {
        super(R.layout.item_rand_numball);
    }

    @Override
    protected void convert(BaseViewHolder helper, RandBallEntity item) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(buttonDim, buttonDim);
        helper.getView(R.id.button).setLayoutParams(layoutParams);
        String ballText = "" + ((item.getBallNum() / 10 == 0) ? "0"+item.getBallNum() : item.getBallNum());
        ((TextView)helper.getView(R.id.inner_tv)).setText(ballText);

        helper.getView(R.id.button).setTag(item.getBallNum());
        helper.getView(R.id.button).setBackground(Util.getOvalStateListBitmapDrawable(
                helper.getView(R.id.button),
                buttonDim,
                normalColor,
                Color.GRAY,
                Color.GRAY));
    }
}
