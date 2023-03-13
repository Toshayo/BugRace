package net.toshayo.android.bugrace;

import android.graphics.Bitmap;

public class Enemy extends BaseSprite {
    protected Enemy(Bitmap sprite, int x, int y, int width, int height, boolean isAnimated) {
        super(sprite, x, y, width, height, isAnimated);
    }
}
