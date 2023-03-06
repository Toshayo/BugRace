package net.toshayo.android.bugrace;

import android.graphics.drawable.Drawable;

public class Player extends BaseSprite implements IUpdatable {
    private int _movement;

    protected Player(Drawable sprite, int x, int y, int width, int height) {
        super(sprite, x, y, width, height);
    }

    public void setMovement(int movement) {
        this._movement = movement;
    }

    @Override
    public void update() {
        x += _movement;
    }
}
