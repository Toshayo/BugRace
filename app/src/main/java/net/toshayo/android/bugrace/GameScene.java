package net.toshayo.android.bugrace;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

public class GameScene extends View implements IUpdatable, IObservable {
    public static final int INTERVAL = 1000 / 20;
    private int _score;
    public static final int TWO_SECONDS_DELAY = (int)(2 * (1000F / INTERVAL));
    private final List<Enemy> _enemies;
    private final Player _player;
    private final World _world;

    private final List<Integer> listTypeEnemies;

    private final Map<Integer, Bitmap> _cachedSprites;

    private boolean _isInitialized, _isPaused;
    private final Paint painter;
    private int _collisionTicks, _carSpawnTicks, carWidth, carHeight;

    private final Paint paint;
    private final MediaPlayer _mediaPlayer;
    private final Timer _timer;
    private final List<IObserver> _observers;
    private final Random _rnd;

    public GameScene(Context context, AttributeSet attrs) {
        super(context, attrs);
        _rnd = new Random();
        _isPaused = false;
        _observers = new ArrayList<>();
        listTypeEnemies = List.of(R.drawable.car_red, R.drawable.oil, R.drawable.bug);
        _cachedSprites = new HashMap<>();
        for(int id : listTypeEnemies) {
            _cachedSprites.put(id, BitmapFactory.decodeResource(getResources(), id));
        }
        _score = 0;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);
        _player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.car), 0, 0, 0, 0);
        _world = new World(ResourcesCompat.getDrawable(getResources(), R.drawable.track, null));
        _enemies = new ArrayList<>();
        painter = new Paint();
        painter.setColor(Color.CYAN);
        painter.setStyle(Paint.Style.FILL);
        _collisionTicks = 0;
        resetCarSpawnTime();

        _mediaPlayer = MediaPlayer.create(context, R.raw.bgm);

        _timer = new Timer();
        UpdateGameTask task = new UpdateGameTask(this);
        _timer.schedule(task, 0, INTERVAL);
    }

    public void pause() {
        _isPaused = true;
        _mediaPlayer.pause();
    }

    public void resume() {
        _isPaused = false;
        _mediaPlayer.start();
    }

    public void update() {
        if(_isPaused) {
            return;
        }

        if(!_player.isAlive()) {
            gameOver();
            return;
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
            int randomEnemy = getRandomElement(listTypeEnemies);
            _enemies.add(new Enemy(
                    _cachedSprites.get(randomEnemy),
                    (int)(Math.random() * (getWidth() - 3 * _player.width) + _player.width),
                    0,
                    carWidth,
                    carHeight,
                    randomEnemy == R.drawable.bug
            ));
            resetCarSpawnTime();
        }
        // Redraw the scene
        invalidate();
    }

    public void gameOver() {
        _mediaPlayer.stop();
        _timer.cancel();
        notifyObservers();
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
        _mediaPlayer.setLooping(true);
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
        return list.get(_rnd.nextInt(list.size()));
    }

    @Override
    public void attach(IObserver observer) {
        if(_observers.contains(observer)) {
            return;
        }

        _observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        _observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for(IObserver observer : _observers) {
            observer.update(this);
        }
    }

    public int getScore() {
        return _score;
    }
}
