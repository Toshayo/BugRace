package net.toshayo.android.bugrace;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Game implements IUpdatable {
    public static final int INTERVAL = 1000 / 20;
    private final List<BaseSprite> _sprites;
    private int _width, _height;

    public Game() {
        Timer timer = new Timer();
        UpdateGameTask task = new UpdateGameTask(this);
        timer.schedule(task, 0, INTERVAL);

        _sprites = new ArrayList<>();
    }

    public void init(int width, int height) {
        _width = width;
        _height = height;
        _sprites.clear();
        _sprites.add(new BaseSprite(width / 2, (int)(height * 0.9), (int)(width * 0.1), (int)(width * 0.2)));
    }

    @Override
    public void update() {
        // Update the game
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(0x3333FF);
        canvas.drawRect(0, 0, _width, _height, paint);
        for(BaseSprite sprite : _sprites)
            sprite.draw(canvas);
    }
}
