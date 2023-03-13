package net.toshayo.android.bugrace;

import android.graphics.Bitmap;

public class Enemy extends BaseSprite {
    public final boolean isMoving;

    protected Enemy(Bitmap sprite, int x, int y, int width, int height, boolean isAnimated,  boolean isMoving) {
        super(sprite, x, y, width, height, isAnimated);
        this.isMoving = isMoving;
    }
}
