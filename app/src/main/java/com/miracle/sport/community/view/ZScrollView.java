package com.miracle.sport.community.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.miracle.sport.home.view.MyScrollview;

/**
 * Created by Michael on 2018/10/27 15:44 (星期六)
 */
public class ZScrollView extends MyScrollview {

    private View childView;
    private View desView;
    private int[] childPosition = new int[2];
    private int[] desPosition = new int[2];

    public ZScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViews(View childView, View desView) {
        this.childView = childView;
        this.desView = desView;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (desView != null && childView != null) {
            childView.getLocationOnScreen(childPosition);
            desView.getLocationOnScreen(desPosition);

            if (childPosition[1] <= desPosition[1]) {
                desView.setVisibility(VISIBLE);
                childView.setVisibility(INVISIBLE);
            } else {
                desView.setVisibility(INVISIBLE);
                childView.setVisibility(VISIBLE);
            }
        }

        Log.e("ZZ", "childView:" + childPosition[1]);
        Log.e("ZZ", "desView:" + desPosition[1]);
    }
}
