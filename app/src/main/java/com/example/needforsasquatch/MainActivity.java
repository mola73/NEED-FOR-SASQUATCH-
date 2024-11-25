package com.example.needforsasquatch;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static MediaPlayer menuMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and start menu music
        menuMusic = MediaPlayer.create(this, R.raw.start_menu_music);
        menuMusic.setLooping(true);
        menuMusic.start();

        ImageButton startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {//this the button will send the user to the mode slection page
            @Override
            public void onClick(View v) {
                // Navigate to mode selection
                Intent intent = new Intent(MainActivity.this, ModeSelectionActivity.class);
                startActivity(intent);
            }
        });
    }

    // Static method to stop menu music from other activities
    public static void stopMenuMusic() {
        if (menuMusic != null && menuMusic.isPlaying()) {
            menuMusic.stop();
            menuMusic.release();
            menuMusic = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopMenuMusic(); // Ensure music is stopped if the app is closed
    }
}
