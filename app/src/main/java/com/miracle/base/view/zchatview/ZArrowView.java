package com.miracle.base.view.zchatview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.miracle.R;


/**
 * Created by NaOH on 2018/7/17 10:33 (星期二).
 */
public class ZArrowView extends View {

    //箭头的长度
    private int arrowLength = 30;
    private final Paint mPaint;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private int orentitaon;

    public ZArrowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        initAttrs(context.obtainStyledAttributes(attrs, R.styleable.ZArrowView));
    }

    private void initAttrs(TypedArray ta) {
        orentitaon = ta.getInt(ta.getIndex(R.styleable.ZArrowView_ZArrowView_orientation), HORIZONTAL);
        ta.recycle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mWidth = getWidth();
        int mHeight = getHeight();
        if (orentitaon == HORIZONTAL) {
            //线宽度
            int lineWidth = mHeight / 3;
            //直线
            canvas.drawRect(0, lineWidth, mWidth - arrowLength, lineWidth * 2, mPaint);
            //箭头
            Path path = new Path();
            path.moveTo(mWidth - arrowLength, 0);
            path.lineTo(mWidth, mHeight / 2);
            path.lineTo(mWidth - arrowLength, mHeight);
            path.close();
            canvas.drawPath(path, mPaint);
        } else {
            //线宽度
            int lineWidth = mWidth / 3;
            //直线
            canvas.drawRect(lineWidth, arrowLength, lineWidth * 2, mHeight, mPaint);
            //箭头
            Path path = new Path();
            path.moveTo(0, arrowLength);
            path.lineTo(mWidth / 2, 0);
            path.lineTo(mWidth, arrowLength);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

}
