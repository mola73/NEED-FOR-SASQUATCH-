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
    private long startTime;
    private boolean isGameOver = false;  // Track if game is over
    private int carSpeed = 3000;  // Initial speed of oncoming cars (3000ms)
    private static final int SPEED_INCREASE_INTERVAL = 10000;  // Increase speed every 10 seconds
    private static final int SPEED_INCREMENT = 200;  // Reduce animation duration by 200ms per interval

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        MainActivity.stopMenuMusic();  // Stop menu music

        mainCar = findViewById(R.id.main_car);

        // Initialize and start driving sound
        drivingSound = MediaPlayer.create(this, R.raw.driving);
        drivingSound.setLooping(true);
        drivingSound.start();

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

        startTime = System.currentTimeMillis();  // Start the timer

        // Start spawning oncoming cars and initiate speed increases
        spawnOncomingCars();
        increaseSpeedOverTime();
    }

    private void moveCarToLane(float x) {
        float targetX;
        if (x < laneWidth) {
            targetX = (laneWidth - carWidth) / 2f;  // Left lane
        } else if (x < laneWidth * 2) {
            targetX = laneWidth + (laneWidth - carWidth) / 2f;  // Middle lane
        } else {
            targetX = 2 * laneWidth + (laneWidth - carWidth) / 2f;  // Right lane
        }
        mainCar.animate().x(targetX).setDuration(300).start();
    }

    private void spawnOncomingCars() {
        handler.postDelayed(() -> {
            if (!isGameOver) {
                createOncomingCar();
                spawnOncomingCars();
            }
        }, 2000);
    }

    private void createOncomingCar() {
        final ImageView oncomingCar = new ImageView(this);
        oncomingCar.setImageResource(R.drawable.oncoming_car);
        oncomingCar.setLayoutParams(new RelativeLayout.LayoutParams(
                mainCar.getLayoutParams().width, mainCar.getLayoutParams().height));

        int lane = random.nextInt(3);
        float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
        oncomingCar.setX(startX);
        oncomingCar.setY(-200);

        RelativeLayout road = findViewById(R.id.road);
        road.addView(oncomingCar);

        oncomingCar.animate()
                .translationY(getResources().getDisplayMetrics().heightPixels)
                .setDuration(carSpeed)  // Use current speed
                .withEndAction(() -> road.removeView(oncomingCar))
                .start();

        checkCollision(oncomingCar);
    }

    private void checkCollision(ImageView oncomingCar) {
        handler.postDelayed(() -> {
            if (!isGameOver && isCollision(mainCar, oncomingCar)) {
                gameOver();  // Trigger game over
            } else if (!isGameOver) {
                checkCollision(oncomingCar);  // Keep checking for collision
            }
        }, 50);
    }

    private boolean isCollision(View v1, View v2) {
        int[] pos1 = new int[2];
        int[] pos2 = new int[2];
        v1.getLocationOnScreen(pos1);
        v2.getLocationOnScreen(pos2);

        return !(pos1[0] + v1.getWidth() < pos2[0] || pos1[0] > pos2[0] + v2.getWidth() ||
                pos1[1] + v1.getHeight() < pos2[1] || pos1[1] > pos2[1] + v2.getHeight());
    }

    private void gameOver() {
        isGameOver = true;
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

        Intent intent = new Intent(DashModeActivity.this, GameOverActivity.class);
        intent.putExtra("SCORE", elapsedTime);  // Pass score to GameOverActivity
        startActivity(intent);
        finish();
    }

    private void increaseSpeedOverTime() {
        handler.postDelayed(() -> {
            if (!isGameOver) {
                // Decrease the animation duration, increasing speed
                carSpeed = Math.max(carSpeed - SPEED_INCREMENT, 500);
                increaseSpeedOverTime();  // Schedule the next speed increase
            }
        }, SPEED_INCREASE_INTERVAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drivingSound != null) {
            drivingSound.release();
        }
    }
}
