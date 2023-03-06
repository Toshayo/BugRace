package net.toshayo.android.bugrace;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

public class BaseSprite implements IDrawable {
    private final Drawable _sprite;
    protected int x, y, width, height;

    protected BaseSprite(Drawable sprite, int x, int y, int width, int height) {
        _sprite = sprite;
        setPosition(x, y);
        this.width = width;
        this.height = height;
    }

    public void setPosition(int x, int y) {
        this.x = (int)(x - width / 2F);
        this.y = (int)(y - height / 2F);
    }

    public void setSize(int width, int height) {
        this.x = (int)(this.x + this.width / 2F);
        this.y = (int)(this.y + this.height / 2F);
        this.width = width;
        this.height = height;
        setPosition(this.x, this.y);
    }

    public boolean intersectsWith(BaseSprite other) {
        return !(x + width < other.x || other.x + other.width < x || y + height < other.y || other.y + other.height < y);
    }

    @Override
    public void draw(Canvas canvas) {
        _sprite.setBounds(x, y, x + width, y + height);
        _sprite.draw(canvas);
    }
}
