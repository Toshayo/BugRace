package net.toshayo.android.bugrace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        ((TextView)findViewById(R.id.lblScore)).setText(getString(R.string.lblScore, getIntent().getIntExtra("score", 0)));

        findViewById(R.id.btnStart).setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
    }
}