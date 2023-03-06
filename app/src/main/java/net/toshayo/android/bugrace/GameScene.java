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
    private final List<IDrawable> _sprites;
    private boolean _isInitialized;
    private final Paint painter;
    private int _collisionTicks;

    public GameScene(Context context, AttributeSet attrs) {
        super(context, attrs);

        Timer timer = new Timer();
        UpdateGameTask task = new UpdateGameTask(this);
        timer.schedule(task, 0, INTERVAL);

        _sprites = new ArrayList<>();
        painter = new Paint();
        painter.setColor(Color.CYAN);
        painter.setStyle(Paint.Style.FILL);
        _collisionTicks = 0;
    }

    public void update() {
        int step = (int)(getHeight() * (_collisionTicks > 0 ? 0.005 : 0.01));
        for(IDrawable sprite : _sprites) {
            if(sprite instanceof Player) {
                Player player = (Player) sprite;
                for(IDrawable otherSprite : _sprites) {
                    if(otherSprite instanceof EnemyCar) {
                        EnemyCar car = (EnemyCar) otherSprite;
                        if(player.intersectsWith(car)) {
                            _collisionTicks = (int)(3 * (1000F / INTERVAL));
                        }
                    }
                }
            }
            if(sprite instanceof EnemyCar) {
                EnemyCar car = (EnemyCar) sprite;
                car.y += step;
            }
            if(sprite instanceof World) {
                World world = (World) sprite;
                world.move(step);
            }
        }
        if(_collisionTicks > 0)
            _collisionTicks -= 1;
        // Redraw the scene
        invalidate();
    }

    private void init(int width, int height) {
        _sprites.clear();
        _sprites.add(new Player(ResourcesCompat.getDrawable(getResources(), R.drawable.car, null), width / 2, (int)(height * 0.9), (int)(width * 0.1), (int)(width * 0.2)));
        _sprites.add(new World());
        _isInitialized = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!_isInitialized)
            init(getWidth(), getHeight());
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), painter);
        for(IDrawable sprite : _sprites)
            sprite.draw(canvas);
    }
}
