package com.gjiazhe.ggprogressbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by gjz on 12/11/15.
 */
public class GGProgressBar extends View {

    protected final int DEFAULT_REACHED_BAR_COLOR = 0xFF3F51B5;
    protected final int DEFAULT_UNREACHED_BAR_COLOR = 0x553F51B5;
    protected final int DEFAULT_TEXT_COLOR = 0xFF3F51B5;

    protected Paint mReachedBarPaint;
    protected Paint mUnreachedBarPaint;
    protected Paint mTextPaint;

    protected boolean mIfDrawUnreachedBar = true;
    protected boolean mIfDrawReachedBar = true;

    protected int mReachedBarColor;
    protected int mUnreachedBarColor;
    protected RectF mReachedRectF = new RectF();
    protected RectF mUnreachedRectF = new RectF();

    protected int mMaxProgress;
    protected int mActualProgress;
    protected int mShownProgress;

    protected boolean mIfDrawText;
    protected String mDrawText;
    protected float mTextSize;
    protected int mTextColor;
    protected float mTextOffset;
    /**
     * The drawn text start.
     */
    protected float mDrawTextX;

    /**
     * The drawn text end.
     */
    protected float mDrawTextY;
    /**
     * The suffix of the number.
     */
    protected String mSuffix;

    /**
     * The prefix.
     */
    protected String mPrefix;

    protected int mProgressDuration;
    protected ValueAnimator mProgressAnimator;

    public GGProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final float default_text_size = sp2px(10);
        final float default_progress_text_offset = dp2px(3.0f);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GGProgressBar, defStyleAttr, 0);
        mReachedBarColor = a.getColor(R.styleable.GGProgressBar_gpb_reached_bar_color, DEFAULT_REACHED_BAR_COLOR);

        mIfDrawUnreachedBar = a.getBoolean(R.styleable.GGProgressBar_gpb_unreached_bar_visibility, true);
        mUnreachedBarColor = a.getColor(R.styleable.GGProgressBar_gpb_unreached_bar_color, DEFAULT_UNREACHED_BAR_COLOR);

        mIfDrawText = a.getBoolean(R.styleable.GGProgressBar_gpb_text_visibility, true);
        if (mIfDrawText) {
            mTextOffset = a.getDimension(R.styleable.GGProgressBar_gpb_text_offset, default_progress_text_offset);
            mTextColor = a.getColor(R.styleable.GGProgressBar_gpb_text_color, DEFAULT_TEXT_COLOR);
            mTextSize = a.getDimension(R.styleable.GGProgressBar_gpb_text_size, default_text_size);
            mPrefix = a.hasValue(R.styleable.GGProgressBar_gpb_prefix) ? a.getString(R.styleable.GGProgressBar_gpb_prefix) : "";
            mSuffix = a.hasValue(R.styleable.GGProgressBar_gpb_suffix) ? a.getString(R.styleable.GGProgressBar_gpb_suffix) : "%";
        }

        mMaxProgress = a.getInt(R.styleable.GGProgressBar_gpb_max, 100);
        mShownProgress = mActualProgress = a.getInt(R.styleable.GGProgressBar_gpb_progress, 0);
        if (mShownProgress > mMaxProgress) {
            mShownProgress = mActualProgress = mMaxProgress;
        }
        mProgressDuration = a.getInt(R.styleable.GGProgressBar_gpb_duration, 1500);

        a.recycle();
    }

    protected void setupPaints() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);

        if (mIfDrawUnreachedBar) {
            mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mUnreachedBarPaint.setColor(mUnreachedBarColor);
        }

        if (mIfDrawText) {
            mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mTextPaint.setColor(mTextColor);
            mTextPaint.setTextSize(mTextSize);
        }
    }


    protected void setupRectFWithText(){}

    protected void setupRectFWithoutText(){}

    /**
     * Get progress text color.
     *
     * @return progress text color.
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Get progress text size.
     *
     * @return progress text size.
     */
    public float getProgressTextSize() {
        return mTextSize;
    }

    public int getUnreachedBarColor() {
        return mUnreachedBarColor;
    }

    public int getReachedBarColor() {
        return mReachedBarColor;
    }

    public int getActualProgress() {
        return mActualProgress;
    }

    public int getShownProgress() {
        return mShownProgress;
    }

    public int getMax() {
        return mMaxProgress;
    }

    public void setProgressTextVisibility(boolean visibility) {
        mIfDrawText = visibility;
        invalidate();
    }

    public boolean getProgressTextVisibility() {
        return mIfDrawText;
    }

    public void setProgressTextSize(float textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setProgressTextColor(int textColor) {
        this.mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setUnreachedBarColor(int barColor) {
        this.mUnreachedBarColor = barColor;
        mUnreachedBarPaint.setColor(mUnreachedBarColor);
        invalidate();
    }

    public void setReachedBarColor(int progressColor) {
        this.mReachedBarColor = progressColor;
        mReachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }


    public void setMax(int maxProgress) {
        if (maxProgress > 0) {
            this.mMaxProgress = maxProgress;
            invalidate();
        }
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            mSuffix = "";
        } else {
            mSuffix = suffix;
        }
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null)
            mPrefix = "";
        else {
            mPrefix = prefix;
        }
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void addProgressBy(int by) {
        if (by > 0) {
            setProgress(mActualProgress + by);
        }
    }

    public void setProgress(int progress) {
        if (progress <= mMaxProgress && progress >= 0) {
            mActualProgress = progress;
        } else if (progress > mMaxProgress) {
            mActualProgress = mMaxProgress;
        }
        startAnim();
    }

    public void resetProgress() {
        if (mProgressAnimator != null && mProgressAnimator.isStarted()) {
            mProgressAnimator.cancel();
        }

        mActualProgress = mShownProgress = 0;
        invalidate();
    }

    private void startAnim() {
        if (mProgressAnimator != null && mProgressAnimator.isStarted()) {
            mProgressAnimator.cancel();
        }

        mProgressAnimator = ValueAnimator.ofInt(mShownProgress, mActualProgress);
        mProgressAnimator.setDuration((long) (mProgressDuration * Math.abs(mActualProgress - mShownProgress) / mMaxProgress));
        mProgressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mShownProgress = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        mProgressAnimator.start();
    }


    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

}