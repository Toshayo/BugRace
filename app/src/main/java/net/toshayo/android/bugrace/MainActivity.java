package net.toshayo.android.bugrace;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the car sprite
        BaseSprite.carDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.car, null);
    }
}