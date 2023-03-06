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

    public void keepInBounds(int width, int height) {
        if(x - this.width < 0) {
            x = this.width;
        } else if(x + this.width * 2 > width) {
            x = width - this.width * 2;
        }
    }
}
