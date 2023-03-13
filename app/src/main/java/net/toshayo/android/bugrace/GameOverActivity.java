package net.toshayo.android.bugrace;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.game_over);
        mediaPlayer.start();

        if(getIntent().hasExtra("score"))
            ((TextView)findViewById(R.id.lblScore)).setText(getString(R.string.lblScore, getIntent().getIntExtra("score", 0)));
        else
            ((TextView)findViewById(R.id.lblScore)).setText("");

        findViewById(R.id.btnStart).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}