package net.toshayo.android.bugrace;

import java.util.TimerTask;

public class UpdateGameTask extends TimerTask {
    private final IUpdatable _game;

    public UpdateGameTask(IUpdatable game) {
        _game = game;
    }

    @Override
    public void run() {
        // Call game update
        _game.update();
    }
}
