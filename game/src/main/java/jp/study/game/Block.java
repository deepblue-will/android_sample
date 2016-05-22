package jp.study.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

public class Block  implements DrawbleItem {
    private final float mTop;
    private final float mLeft;
    private final float mBottom;
    private final float mRight;
    private int mHard = 1;
    private boolean mIsCollision = false;
    private boolean mIsExist = true;
    private static final String KEY_HARD = "hard";

    public Block(float top, float left, float bottom, float right) {
        this.mTop = top;
        this.mLeft = left;
        this.mBottom = bottom;
        this.mRight = right;
    }

    public void draw(Canvas canvas, Paint paint) {
        if(mIsExist) {
            if(mIsCollision) {
                mHard--;
                mIsCollision = false;
                if(mHard <= 0) {
                    mIsExist = false;
                    return;
                }
            }
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(4f);
            canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);
        }
    }

    /**
     * ボールが衝突した時の処理
     */
    public void collision() {
        this.mIsCollision = true;
    }

    public boolean isExist() {
        return mIsExist;
    }

    public Bundle save() {
        Bundle outState = new Bundle();
        outState.putInt(KEY_HARD, mHard);
        return outState;
    }

    public void restore(Bundle inState) {
        mHard = inState.getInt(KEY_HARD);
        mIsExist = mHard > 0;
    }
 }
