package net.toshayo.android.bugrace;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements IObserver {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mainScene).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_POINTER_DOWN || event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_HOVER_MOVE) {
                if (event.getX() < v.getWidth() / 2F) {
                    ((GameScene) findViewById(R.id.mainScene)).moveLeft();
                } else {
                    ((GameScene) findViewById(R.id.mainScene)).moveRight();
                }
            } else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_POINTER_UP) {
                ((GameScene) findViewById(R.id.mainScene)).stopMovement();
            }

            v.performClick();
            return true;
        });

        ((GameScene) findViewById(R.id.mainScene)).attach(this);
    }

    @Override
    public void update(Object... args) {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("score", ((GameScene) args[0]).getScore());
        startActivity(intent);
    }
}