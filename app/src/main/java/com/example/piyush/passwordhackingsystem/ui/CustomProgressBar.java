package com.example.piyush.passwordhackingsystem.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by piyush0 on 28/10/16.
 */
public class CustomProgressBar extends View {

    Context context;
    Paint paintForFill;
    Paint paintForStroke;
    float sweepAngle ;
    int color;

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
        invalidate();
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setColor(int color) {
        this.color = color;
        invalidate();
    }

    public CustomProgressBar(Context context) {
        super(context);
        init(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        this.context = context;
        this.paintForFill = new Paint();

        this.paintForFill.setStyle(Paint.Style.FILL);

        this.paintForStroke = new Paint();
        this.paintForStroke.setColor(Color.BLACK);
        this.paintForStroke.setStyle(Paint.Style.STROKE);
        this.paintForStroke.setStrokeWidth(4f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float r = 200;
            this.paintForFill.setColor(color);

            canvas.drawArc(getWidth() / 2 - r, getHeight() / 2 - r, getWidth() / 2 + r, getHeight() / 2 + r, 270, sweepAngle, true, this.paintForFill);
            canvas.drawArc(getWidth() / 2 - r, getHeight() / 2 - r, getWidth() / 2 + r, getHeight() / 2 + r, 270, sweepAngle, true, this.paintForStroke);

        }

    }

}
