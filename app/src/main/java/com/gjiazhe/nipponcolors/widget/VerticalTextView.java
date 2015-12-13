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

    public VerticalTextView(Context context) {
        this(context, null);
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupPaint();
    }

    private void setupPaint() {
        textPaint = getPaint();
        textPaint.setColor(getCurrentTextColor());
        textPaint.getTextBounds((String) getText(), 0, getText().length(), bounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (bounds.height() + textPaint.descent()), bounds.width());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.rotate(90, 0, bounds.height());
        canvas.drawText((String) getText(), -bounds.height(), bounds.height() - textPaint.descent(), textPaint);
    }

}


