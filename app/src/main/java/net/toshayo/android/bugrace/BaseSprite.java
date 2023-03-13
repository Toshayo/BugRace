package net.toshayo.android.bugrace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BaseSprite implements IDrawable {
    private final Bitmap _sprite;
    protected int x, y, width, height, tick;
    protected int maxTick;
    protected Paint paint;

    protected BaseSprite(Bitmap sprite, int x, int y, int width, int height, boolean isAnimatedSprite) {
        _sprite = sprite;
        setPosition(x, y);
        this.width = width;
        this.height = height;
        this.tick = 0;
        this.maxTick = isAnimatedSprite ? sprite.getWidth() / sprite.getHeight() : 0;

        paint = new Paint();
    }

    protected BaseSprite(Bitmap sprite, int x, int y, int width, int height) {
        this(sprite, x, y, width, height, false);
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
        if(maxTick > 0) {
            tick += 1;
            tick %= maxTick;
        }
        int x = _sprite.getHeight() * tick;
        canvas.drawBitmap(_sprite, new Rect(x, 0, maxTick > 0 ? x + _sprite.getHeight() : _sprite.getWidth(), _sprite.getHeight()), new Rect(this.x, y, this.x+width, y+height), paint);
    }
}
