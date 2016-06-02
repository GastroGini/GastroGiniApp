package ch.hsr.edu.sinv_56082.gastroginiapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class ConnectionStateView extends View {

    public ConnectionStateView(Context context) {
        super(context);
    }

    public ConnectionStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ConnectionStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    private int color = Color.RED;

    Paint paint = new Paint();

    Rect rects = new Rect(30,30,80,80);

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(color);
        canvas.drawRect(rects, paint);
    }

    void changeColor(int color) {
        this.color = color;
        invalidate();
    }


}
