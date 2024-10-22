package com.example.needforsasquatch;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class DashModeActivity extends AppCompatActivity {

    private ImageView mainCar;
    private Handler handler = new Handler();
    private Random random = new Random();
    private MediaPlayer drivingSound;
    private int screenWidth, laneWidth, carWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        // Stop main menu music when Dash Mode starts
        MainActivity.stopMenuMusic();

        mainCar = findViewById(R.id.main_car);

        // Initialize and start driving sound
        drivingSound = MediaPlayer.create(this, R.raw.driving);
        drivingSound.setLooping(true);
        drivingSound.start();

        // Calculate screen and lane dimensions
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        laneWidth = screenWidth / 3;
        carWidth = mainCar.getLayoutParams().width;

        // Detect lane taps for movement
        findViewById(R.id.road).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                moveCarToLane(event.getX());
            }
            return true;
        });

        // Start spawning oncoming cars
        spawnOncomingCars();
    }

    private void moveCarToLane(float x) {
        float targetX;
        if (x < laneWidth) {
            targetX = (laneWidth - carWidth) / 2f; // Left lane
        } else if (x < laneWidth * 2) {
            targetX = laneWidth + (laneWidth - carWidth) / 2f; // Middle lane
        } else {
            targetX = 2 * laneWidth + (laneWidth - carWidth) / 2f; // Right lane
        }
        mainCar.animate().x(targetX).setDuration(300).start();
    }

    private void spawnOncomingCars() {
        handler.postDelayed(() -> {
            createOncomingCar();
            spawnOncomingCars();
        }, 2000); // Spawn every 2 seconds
    }

    private void createOncomingCar() {
        final ImageView oncomingCar = new ImageView(this);
        oncomingCar.setImageResource(R.drawable.oncoming_car);
        oncomingCar.setLayoutParams(new RelativeLayout.LayoutParams(
                mainCar.getLayoutParams().width, mainCar.getLayoutParams().height));

        int lane = random.nextInt(3); // Random lane selection
        float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
        oncomingCar.setX(startX);
        oncomingCar.setY(-200); // Start above the screen

        RelativeLayout road = findViewById(R.id.road);
        road.addView(oncomingCar);

        oncomingCar.animate()
                .translationY(getResources().getDisplayMetrics().heightPixels)
                .setDuration(3000)
                .withEndAction(() -> road.removeView(oncomingCar))
                .start();

        checkCollision(oncomingCar);
    }

    private void checkCollision(ImageView oncomingCar) {
        handler.postDelayed(() -> {
            // Check if the main car's and oncoming car's bounds intersect
            if (isCollision(mainCar, oncomingCar)) {
                gameOver(); // Trigger game over
            } else {
                // Re-run the collision check
                checkCollision(oncomingCar);
            }
        }, 50); // Check every 50ms
    }

    // Helper method to detect if two views collide
    private boolean isCollision(View v1, View v2) {
        int[] pos1 = new int[2];
        int[] pos2 = new int[2];
        v1.getLocationOnScreen(pos1);
        v2.getLocationOnScreen(pos2);

        int v1Left = pos1[0];
        int v1Right = v1Left + v1.getWidth();
        int v1Top = pos1[1];
        int v1Bottom = v1Top + v1.getHeight();

        int v2Left = pos2[0];
        int v2Right = v2Left + v2.getWidth();
        int v2Top = pos2[1];
        int v2Bottom = v2Top + v2.getHeight();

        return !(v1Right < v2Left || v1Left > v2Right || v1Bottom < v2Top || v1Top > v2Bottom);
    }


    private void gameOver() {
        Intent intent = new Intent(DashModeActivity.this, GameOverActivity.class);
        startActivity(intent);
        finish();
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drivingSound != null) {
            drivingSound.release();
        }
    }
}
