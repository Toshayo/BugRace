package net.toshayo.android.bugrace;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the game and the scene
        Game game = new Game();
        GameScene scene = findViewById(R.id.mainScene);
        scene.setGame(game);
    }
}