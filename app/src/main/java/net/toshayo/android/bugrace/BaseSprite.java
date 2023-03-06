package net.toshayo.android.bugrace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class BaseSprite {
    public static Drawable carDrawable;
    protected int x, y, width, height;

    protected BaseSprite(int x, int y, int width, int height) {
        setPosition(x, y);
        this.width = width;
        this.height = height;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void draw(Canvas canvas) {
        carDrawable.setBounds(x, y, width, height);
        carDrawable.draw(canvas);
    }
}
