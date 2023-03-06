package net.toshayo.android.bugrace;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.mainScene).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (event.getX() < v.getWidth() / 2F) {
                    ((GameScene) findViewById(R.id.mainScene)).moveLeft();
                } else {
                    ((GameScene) findViewById(R.id.mainScene)).moveRight();
                }
            } else {
                ((GameScene) findViewById(R.id.mainScene)).stopMovement();
            }

            v.performClick();
            return true;
        });
    }
}