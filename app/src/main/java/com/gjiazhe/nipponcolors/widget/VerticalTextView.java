package com.gjiazhe.nipponcolors.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by gjz on 12/1/15.
 */
public class VerticalTextView extends TextView {
    private Rect bounds = new Rect();
    private TextPaint textPaint;
    private int color;

    public VerticalTextView(Context context) {
        super(context);
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        color = getCurrentTextColor();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        textPaint = getPaint();
        textPaint.getTextBounds((String) getText(), 0, getText().length(), bounds);
        setMeasuredDimension((int) (bounds.height() + textPaint.descent()), bounds.width());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        textPaint.setColor(color);
        canvas.rotate(90, 0, bounds.height());
        canvas.drawText((String) getText(), -bounds.height(), bounds.height() - textPaint.descent(), textPaint);
    }

}


