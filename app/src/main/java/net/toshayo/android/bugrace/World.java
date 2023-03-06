package net.toshayo.android.bugrace;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

public class World implements IDrawable {
    private final Drawable _worldChunkSprite;
    private Bitmap _world;
    private int _y;

    public World(Drawable worldChunkSprite) {
        _worldChunkSprite = worldChunkSprite;
    }

    public void move(int deltaY) {
        _y += deltaY;
        _y %= _worldChunkSprite.getIntrinsicHeight();
    }

    @Override
    public void draw(Canvas canvas) {
        if(_world == null) {
            _world = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight() + _worldChunkSprite.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas worldCanvas = new Canvas(_world);
            for(int i = 0; i < _world.getHeight(); i += _worldChunkSprite.getIntrinsicHeight()) {
                _worldChunkSprite.setBounds(0, i, _world.getWidth(), i + _worldChunkSprite.getIntrinsicHeight());
                _worldChunkSprite.draw(worldCanvas);
            }
        }
        canvas.drawBitmap(_world, 0, _y - _worldChunkSprite.getIntrinsicHeight(), new Paint());
    }
}
