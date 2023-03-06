package net.toshayo.android.bugrace;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.Nullable;

public class GameScene extends View {
    @Nullable
    private Game _game;

    public GameScene(Context context) {
        super(context);
    }

    public void setGame(Game game) {
        _game = game;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(_game != null)
            _game.draw(canvas);
    }
}
