package com.gjiazhe.ggprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

/**
 * Created by gjz on 12/11/15.
 */
public class GGVerticalProgressBar extends GGProgressBar {

    private float mReachedBarWidth;
    private float mUnreachedBarWidth;

    public GGVerticalProgressBar(Context context) {
        this(context, null);
    }

    public GGVerticalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GGVerticalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final float default_reached_bar_width = dp2px(1.5f);
        final float default_unreached_bar_width = dp2px(1.0f);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GGProgressBar, defStyleAttr, 0);

        mReachedBarWidth = a.getDimension(R.styleable.GGProgressBar_gpb_reached_bar_width, default_reached_bar_width);//default_reached_bar_width;
        mUnreachedBarWidth = a.getDimension(R.styleable.GGProgressBar_gpb_unreached_bar_width, default_unreached_bar_width);//default_unreached_bar_width;

        a.recycle();
        setupPaints();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int width_mode = MeasureSpec.getMode(widthMeasureSpec);
        if (width_mode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            width = getMinWidth() + getPaddingLeft() + getPaddingRight();
        }
        setMeasuredDimension(width, MeasureSpec.getSize(heightMeasureSpec));
    }

    private int getMinWidth() {
        int result = Math.max((int)mReachedBarWidth, (int)mUnreachedBarWidth);
        if (mIfDrawText) {
            result = Math.max(result, (int)(mTextPaint.measureText(mPrefix + "100" + mSuffix)));
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIfDrawText){
            setupRectFWithText();
            canvas.drawText(mDrawText, mDrawTextX, mDrawTextY, mTextPaint);
        }else {
            setupRectFWithoutText();
        }

        if (mIfDrawReachedBar) {
            canvas.drawRect(mReachedRectF, mReachedBarPaint);
        }
        if (mIfDrawUnreachedBar && mUnreachedRectF.top < getHeight() - getPaddingBottom()) {
            canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
        }
    }

    @Override
    protected void setupRectFWithoutText() {
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getPaddingTop();
        mReachedRectF.right = mReachedRectF.left + mReachedBarWidth;
        mReachedRectF.bottom = getPaddingTop() +  (getHeight() - getPaddingTop() - getPaddingBottom()) / (mMaxProgress * 1.0f) * mShownProgress;

        if (mIfDrawUnreachedBar) {
            mUnreachedRectF.left = getPaddingLeft();
            mUnreachedRectF.top = mReachedRectF.bottom;
            mUnreachedRectF.right = mUnreachedRectF.left + mUnreachedBarWidth;
            mUnreachedRectF.bottom = getHeight() - getPaddingBottom();
        }
    }

    @Override
    protected void setupRectFWithText() {
        mDrawText = String.format("%3d", mShownProgress * 100 / mMaxProgress);
        mDrawText = mPrefix + mDrawText + mSuffix;

        float drawTextWidth = mTextPaint.measureText(mPrefix + "100" + mSuffix);
        mDrawTextX = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight())/2.0f - drawTextWidth/2.0f;

        if (mShownProgress == 0) {
            mIfDrawReachedBar = false;
            mDrawTextY = getPaddingTop() + mTextSize/2.0f - (mTextPaint.descent() + mTextPaint.ascent())/2.0f;
        } else {
            mIfDrawReachedBar = true;
            mReachedRectF.left = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight())/2.0f - mReachedBarWidth/2.0f;
            mReachedRectF.top = getPaddingTop();
            mReachedRectF.right = mReachedRectF.left + mReachedBarWidth;
            mReachedRectF.bottom = getPaddingTop() +  (getHeight() - getPaddingTop() - getPaddingBottom()) / (mMaxProgress * 1.0f) * mShownProgress - mTextOffset;
            mDrawTextY = mReachedRectF.bottom + mTextOffset + mTextSize/2.0f - (mTextPaint.descent() + mTextPaint.ascent())/2.0f;
        }

        if (mDrawTextY + mTextPaint.descent() > getHeight() - getPaddingBottom()) {
            mDrawTextY = getHeight() - getPaddingBottom() - mTextPaint.descent();
            mReachedRectF.bottom = mDrawTextY - mTextOffset - mTextSize/2.0f + (mTextPaint.descent() + mTextPaint.ascent())/2.0f;
        }

        if (mIfDrawUnreachedBar) {
            mUnreachedRectF.left = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight())/2.0f - mUnreachedBarWidth/2.0f;
            mUnreachedRectF.top = mDrawTextY + mTextPaint.descent() + mTextOffset;
            mUnreachedRectF.right = mUnreachedRectF.left + mUnreachedBarWidth;
            mUnreachedRectF.bottom = getHeight() - getPaddingBottom();
        }
    }

    public void setReachedBarWidth(float width) {
        mReachedBarWidth = width;
    }

    public void setUnreachedBarWidth(float width) {
        mUnreachedBarWidth = width;
    }

    public float getReachedBarWidth() {
        return mReachedBarWidth;
    }

    public float getUnreachedBarWidth() {
        return mUnreachedBarWidth;
    }
}

