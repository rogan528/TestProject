package com.zhangbin.testproject;
import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

public class DragLayout extends LinearLayout {

    private ViewDragHelper mDragger;

    private ViewDragHelper.Callback callback;
    public DragLayout(Context context) {
        super(context);

    }

    public DragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        callback = new DraggerCallBack();
        //第二个参数就是滑动灵敏度的意思 可以随意设置
        mDragger = ViewDragHelper.create(this, 1.0f, callback);
    }

    class DraggerCallBack extends ViewDragHelper.Callback {

        //这个地方实际上函数返回值为true就代表可以滑动 为false 则不能滑动
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }
//这个地方实际上left就代表 你将要移动到的位置的坐标。返回值就是最终确定的移动的位置。
        // 我们要让view滑动的范围在我们的layout之内
        //实际上就是判断如果这个坐标在layout之内 那我们就返回这个坐标值。
        //如果这个坐标在layout的边界处 那我们就只能返回边界的坐标给他。不能让他超出这个范围
        //除此之外就是如果你的layout设置了padding的话，也可以让子view的活动范围在padding之内的.

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            //取得左边界的坐标
            final int leftBound = getPaddingLeft();
            //取得右边界的坐标
            final int rightBound = getWidth() - child.getWidth() - leftBound;
            //这个地方的含义就是 如果left的值 在leftbound和rightBound之间 那么就返回left
            //如果left的值 比 leftbound还要小 那么就说明 超过了左边界 那我们只能返回给他左边界的值
            //如果left的值 比rightbound还要大 那么就说明 超过了右边界，那我们只能返回给他右边界的值
            return Math.min(Math.max(left, leftBound), rightBound);
        }

        //纵向的注释就不写了 自己体会
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - child.getHeight() - topBound;
            return Math.min(Math.max(top, topBound), bottomBound);
        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //决定是否拦截当前事件
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //处理事件
        mDragger.processTouchEvent(event);
        return true;
    }


}
