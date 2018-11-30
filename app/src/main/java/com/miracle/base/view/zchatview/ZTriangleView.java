package com.miracle.base.view.zchatview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by NaOH on 2018/7/17 17:27 (星期二).
 */
public class ZTriangleView extends View {

    private Paint mPaint;

    public ZTriangleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(width, height / 2);
        path.lineTo(0, height);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
