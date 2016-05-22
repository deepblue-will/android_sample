package jp.study.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Pad implements DrawbleItem{
    private final float mTop;
    private float mLeft;
    private final float mBottom;
    private float mRight;

    public Pad(float top, float bottom) {
        this.mTop = top;
        this.mBottom = bottom;
    }

    public void setLeftRight(float left, float right) {
        mLeft = left;
        mRight = right;
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);
    }

    public float getTop() {
        return mTop;
    }
}
