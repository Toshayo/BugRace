package net.toshayo.android.bugrace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class Player extends BaseSprite implements IUpdatable {
    private int _movement;
    private int _damaged;
    private int _life = 5;

    protected Player(Bitmap sprite, int x, int y, int width, int height) {
        super(sprite, x, y, width, height);

    }

    public void setMovement(int movement) {
        this._movement = movement;
    }
    public void setDamaged(int damaged) {
        this._damaged = damaged;
    }
    public int getLife() {
        return _life;
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

    public void lostALife() {
        if(_life > 0) {
            _life--;
        }
    }

    public boolean isAlive() {
        return _life != 0;
    }

    @Override
    public void draw(Canvas canvas) {
        if(_damaged == 0){
            super.draw(canvas);
        }
    }
}
