package net.toshayo.android.bugrace;

import android.graphics.Canvas;

import java.util.Timer;

public class Game implements IUpdatable {
    public static final int INTERVAL = 1000 / 20;

    public Game() {
        Timer timer = new Timer();
        UpdateGameTask task = new UpdateGameTask(this);
        timer.schedule(task, 0, INTERVAL);
    }

    @Override
    public void update() {
        // Update the game
    }

    public void draw(Canvas canvas) {

    }
}
