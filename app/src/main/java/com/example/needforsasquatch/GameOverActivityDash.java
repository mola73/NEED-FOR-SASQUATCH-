package com.example.needforsasquatch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivityDash extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over_dash);

        ImageView youDiedImage = findViewById(R.id.you_died_dash_image);
        TextView scoreTextView = findViewById(R.id.score_text);

        // Display the score passed from DashModeActivity
        long score = getIntent().getLongExtra("SCORE", 0);
        scoreTextView.setText("Score: " + score);

        // Wait 5 seconds, then restart the game from the main menu
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(GameOverActivityDash.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }, 6000);  // 5-second delay
    }
}
