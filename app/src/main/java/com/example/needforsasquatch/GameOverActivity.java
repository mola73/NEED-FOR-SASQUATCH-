package com.example.needforsasquatch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Find the 'You Died' ImageView
        ImageView youDiedImage = findViewById(R.id.you_died_image);

        // Set OnClickListener to navigate back to ModeSelectionActivity
        youDiedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this, ModeSelectionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
