package net.toshayo.android.bugrace;

import android.graphics.Canvas;

public class World implements IDrawable {
    private int _y;

    public void move(int deltaY) {
        _y += deltaY;
    }
    @Override
    public void draw(Canvas canvas) {
        // TODO
    }
}
