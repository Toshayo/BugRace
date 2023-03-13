package net.toshayo.android.bugrace;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class GameScene extends View implements IUpdatable {
    public static final int INTERVAL = 1000 / 20;
    private int _score;
    public static final int TWO_SECONDS_DELAY = (int)(2 * (1000F / INTERVAL));
    private final List<Enemy> _enemies;
    private final Player _player;
    private final World _world;

    private final List<Integer> listTypeEnemies = new ArrayList<Integer>(){};

    private boolean _isInitialized;
    private final Paint painter;
    private int _collisionTicks, _carSpawnTicks, carWidth, carHeight;

    private final Paint paint;
    private final MediaPlayer _mediaPlayer;

    public GameScene(Context context, AttributeSet attrs) {
        super(context, attrs);
        listTypeEnemies.add(R.drawable.car_red);
        listTypeEnemies.add(R.drawable.oil);
        _score = 0;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        _player = new Player(ResourcesCompat.getDrawable(getResources(), R.drawable.car, null), 0, 0, 0, 0);
        _world = new World(ResourcesCompat.getDrawable(getResources(), R.drawable.track, null));
        _enemies = new ArrayList<>();
        painter = new Paint();
        painter.setColor(Color.CYAN);
        painter.setStyle(Paint.Style.FILL);
        _collisionTicks = 0;
        resetCarSpawnTime();

        _mediaPlayer = MediaPlayer.create(context, R.raw.bgm);

        Timer timer = new Timer();
        UpdateGameTask task = new UpdateGameTask(this);
        timer.schedule(task, 0, INTERVAL);
    }

    public void update() {
        if(!_player.isAlive()) {
            gameOver();
        } else {
            _score++;
        }

        int step = (int)(getHeight() * (_collisionTicks > 0 ? 0.05 : 0.1));
        List<Enemy> toRemove = new ArrayList<>();
        for(Enemy enemy : _enemies) {
            if(_player.intersectsWith(enemy)) {
                if(!(_collisionTicks > 0)){
                    _player.lostALife();
                    _collisionTicks = TWO_SECONDS_DELAY;
                }
            }
            enemy.y += step;
            if(enemy.y > getHeight()) {
                toRemove.add(enemy);
            }
        }
        _enemies.removeAll(toRemove);

        _world.move(step);
        _player.update();
        _player.keepInBounds(_world.getWidth(), _world.getHeight());
        if(_collisionTicks > 0) {
            if (_collisionTicks % 10 == 0) {
                _player.setDamaged(1);
            } else {
                _player.setDamaged(0);
            }
        }
        _collisionTicks -= 1;
        if(_carSpawnTicks > 0)
            _carSpawnTicks -= 1;
        if(_carSpawnTicks <= 0) {
            _enemies.add(new Enemy(
                    ResourcesCompat.getDrawable(getResources(),  getRandomElement(listTypeEnemies) , null),
                    (int)(Math.random() * (getWidth() - 3 * _player.width) + _player.width),
                    0,
                    carWidth,
                    carHeight
            ));
            resetCarSpawnTime();
        }
        // Redraw the scene
        invalidate();
    }

    public void gameOver() {

    }

    private void init(int width, int height) {
        _enemies.clear();
        carWidth = (int)(width * 0.1);
        carHeight = (int)(width * 0.2);
        _player.setSize(carWidth, carHeight);
        _player.setPosition(width / 2, (int)(height * 0.85));
        try {
            _mediaPlayer.prepare();
        } catch (IOException|IllegalStateException e) {
            e.printStackTrace();
        }
        _mediaPlayer.start();
        _isInitialized = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(!_isInitialized)
            init(getWidth(), getHeight());
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), painter);
        _world.draw(canvas);
        for(IDrawable sprite : _enemies)
            sprite.draw(canvas);
        _player.draw(canvas);
        canvas.drawText("SCORE : " + _score, getWidth() / 2F, paint.getTextSize(), paint);
        canvas.drawText("LIFE : " + _player.getLife(), getWidth() * 0.1F, paint.getTextSize(), paint);
    }

    public void moveLeft() {
        _player.setMovement((int)(getWidth() * -0.035));
    }

    public void moveRight() {
        _player.setMovement((int)(getWidth() * 0.035));
    }

    public void stopMovement() {
        _player.setMovement(0);
    }

    private void resetCarSpawnTime() {
        _carSpawnTicks = (int)(TWO_SECONDS_DELAY * 0.5F);
    }

    public int getRandomElement(List<Integer> list) {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
}
