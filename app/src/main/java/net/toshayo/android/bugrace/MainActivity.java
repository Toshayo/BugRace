package net.toshayo.android.bugrace;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the game and the scene
        BaseSprite.carDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_launcher_background, null);
        Game game = new Game();
        GameScene scene = findViewById(R.id.mainScene);
        scene.setGame(game);
    }
}