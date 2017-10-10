package com.example.administrator.simplelayoutmanager;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by WangXing on 2017/10/9.
 */

public class SimpleLayoutManager extends RecyclerView.LayoutManager {
    private String TAG = this.getClass().getSimpleName();

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        detachAndScrapAttachedViews(recycler);
        calculateChildrenSite(recycler);
    }


    private void calculateChildrenSite(RecyclerView.Recycler recycler) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        final int distance = 300;
        double offsetAngle = 22.5;
        Log.i(TAG, "itemCount = " + getItemCount());
        for (int i = 0; i < getItemCount(); i++) {
            // 遍历Recycler中保存的View取出来
            final View view = recycler.getViewForPosition(i);
            addView(view); // 因为刚刚进行了detach操作，所以现在可以重新添加

            measureChildWithMargins(view, 0, 0); // 通知测量view的margin值
            int width = getDecoratedMeasuredWidth(view); // 计算view实际大小，包括了ItemDecorator中设置的偏移量。
            int height = getDecoratedMeasuredHeight(view);

            Rect mTmpRect = new Rect();
            //调用这个方法能够调整ItemView的大小，以除去ItemDecorator。
            calculateItemDecorationsForChild(view, mTmpRect);

            // 调用这句我们指定了该View的显示区域，并将View显示上去，此时所有区域都用于显示View，
            //包括ItemDecorator设置的距离。
            if (i < 1) {
                layoutDecorated(view, centerX - width / 2, centerY - height / 2, centerX + width / 2, centerY + height / 2);
            } else if (i >= 1 && i < 9) {
                int offsetX = ((Double) (distance * Math.cos(Math.toRadians(offsetAngle)))).intValue();
                int offsetY = ((Double) (distance * Math.sin(Math.toRadians(offsetAngle)))).intValue();
                layoutDecorated(view, centerX - width / 2 + offsetX, centerY - height / 2 + offsetY, centerX + width / 2 + offsetX, centerY + height / 2 + offsetY);

                ValueAnimator scaleX = ValueAnimator.ofFloat(-offsetX, 0);
                scaleX.setTarget(view);
                scaleX.setDuration(1000);
                scaleX.addUpdateListener(new SimpleAnimatorUpdateListener(offsetAngle, view));
                scaleX.start();
                Log.i(TAG, i + " offsetX= " + offsetX + ",offsetY= " + offsetY + ",offsetAngle= " + offsetAngle);
                offsetAngle += 45;
            } else {
                int offsetX = ((Double) (1.5 * distance * Math.cos(Math.toRadians(offsetAngle + 22.5)))).intValue();
                int offsetY = ((Double) (1.5 * distance * Math.sin(Math.toRadians(offsetAngle + 22.5)))).intValue();
                layoutDecorated(view, centerX - width / 2 + offsetX, centerY - height / 2 + offsetY, centerX + width / 2 + offsetX, centerY + height / 2 + offsetY);
                Log.i(TAG, i + " offsetX= " + offsetX + ",offsetY= " + offsetY + ",offsetAngle= " + offsetAngle);

                if (offsetX == 0) {
                    offsetX = offsetY;//y轴上的特殊情况
                }
                ValueAnimator scaleX = ValueAnimator.ofFloat(-offsetX, 0);
                scaleX.setTarget(view);
                scaleX.setDuration(2000);
                scaleX.addUpdateListener(new SimpleAnimatorUpdateListener(offsetAngle + 22.5, view));
                scaleX.start();

                offsetAngle += 45;
            }
            Log.i(TAG, i + " width = " + width + ",height= " + height + ",offsetX= " + centerX + ",offsetY= " + centerY);
        }
    }

    class SimpleAnimatorUpdateListener implements ValueAnimator.AnimatorUpdateListener {
        double offsetAngle;
        View view;

        public SimpleAnimatorUpdateListener(double offsetAngle, View view) {
            this.offsetAngle = offsetAngle;
            this.view = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            if (offsetAngle % 90 == 0) {
                if (offsetAngle % 180 == 0) {
                    view.setTranslationX((Float) animation.getAnimatedValue());//x轴上的特殊情况
                } else {
                    view.setTranslationY((Float) animation.getAnimatedValue());//y轴上的特殊情况
                }
            } else {
                view.setTranslationX((Float) animation.getAnimatedValue());
                view.setTranslationY(((Double) ((Float) animation.getAnimatedValue() * Math.tan(Math.toRadians(offsetAngle)))).floatValue());
            }
        }
    }
}