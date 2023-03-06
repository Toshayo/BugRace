package net.toshayo.android.bugrace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameScene extends View implements IUpdatable {
    public static final int INTERVAL = 1000 / 20;
    private final List<BaseSprite> _sprites;
    private boolean _isInitialized;
    private final Paint painter;

    public GameScene(Context context, AttributeSet attrs) {
        super(context, attrs);

        Timer timer = new Timer();
        UpdateGameTask task = new UpdateGameTask(this);
        timer.schedule(task, 0, INTERVAL);

        _sprites = new ArrayList<>();
        painter = new Paint();
        painter.setColor(Color.CYAN);
        painter.setStyle(Paint.Style.FILL);
    }

    public void update() {
        // Redraw the scene
        invalidate();
    }

    private void init(int width, int height) {
        _sprites.clear();
        _sprites.add(new BaseSprite(width / 2, (int)(height * 0.9), (int)(width * 0.1), (int)(width * 0.2)));
        _isInitialized = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!_isInitialized)
            init(getWidth(), getHeight());
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), painter);
        for(BaseSprite sprite : _sprites)
            sprite.draw(canvas);
    }
}
