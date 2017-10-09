package com.example.administrator.simplelayoutmanager;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by WangXing on 2017/10/9.
 */

public class CircleBackgroundTextView extends View {
    private Paint ciclePaint;
    private Paint textPaint;

    int left;
    int top;
    int right;
    int bottom;

    public CircleBackgroundTextView(Context context) {
        super(context);
        ciclePaint = new Paint();
        textPaint = new TextPaint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
