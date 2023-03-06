package net.toshayo.android.bugrace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class GameScene extends View implements IUpdatable {
    public static final int INTERVAL = 1000 / 20;
    private final List<EnemyCar> _enemyCars;
    private final Player _player;
    private final World _world;
    private boolean _isInitialized;
    private final Paint painter;
    private int _collisionTicks;

    public GameScene(Context context, AttributeSet attrs) {
        super(context, attrs);

        _player = new Player(ResourcesCompat.getDrawable(getResources(), R.drawable.car, null), 0, 0, 0, 0);
        _world = new World(ResourcesCompat.getDrawable(getResources(), R.drawable.track, null));
        _enemyCars = new ArrayList<>();
        painter = new Paint();
        painter.setColor(Color.CYAN);
        painter.setStyle(Paint.Style.FILL);
        _collisionTicks = 0;

        Timer timer = new Timer();
        UpdateGameTask task = new UpdateGameTask(this);
        timer.schedule(task, 0, INTERVAL);
    }

    public void update() {
        int step = (int)(getHeight() * (_collisionTicks > 0 ? 0.01 : 0.1));
        for(EnemyCar enemyCar : _enemyCars) {
            if(_player.intersectsWith(enemyCar)) {
                _collisionTicks = (int)(3 * (1000F / INTERVAL));
            }
            enemyCar.y += step;
        }
        _world.move(step);
        _player.update();
        _player.keepInBounds(_world.getWidth(), _world.getHeight());
        if(_collisionTicks > 0)
            _collisionTicks -= 1;
        // Redraw the scene
        invalidate();
    }

    private void init(int width, int height) {
        _enemyCars.clear();
        _player.setSize((int)(width * 0.1), (int)(width * 0.2));
        _player.setPosition(width / 2, (int)(height * 0.85));
        _isInitialized = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!_isInitialized)
            init(getWidth(), getHeight());
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), painter);
        _world.draw(canvas);
        for(IDrawable sprite : _enemyCars)
            sprite.draw(canvas);
        _player.draw(canvas);
    }


    public void moveLeft() {
        _player.setMovement((int)(getWidth() * -0.015));
    }

    public void moveRight() {
        _player.setMovement((int)(getWidth() * 0.015));
    }

    public void stopMovement() {
        _player.setMovement(0);
    }
}
