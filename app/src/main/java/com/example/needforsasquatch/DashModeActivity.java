package com.example.needforsasquatch;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import BackendInfo.Mode.DashMode;

public class DashModeActivity extends AppCompatActivity {
    private DashMode backend;
    private long endScore;
    private long timeElapsed;
    private ImageView mainCar;
    private Handler handler = new Handler();
    private Random random = new Random();
    private MediaPlayer drivingSound;
    private int screenWidth, screenHeight, laneWidth, carWidth, carHeight;
    private double startTime;
    private boolean isGameOver = false;
    private int carSpeed = 3000;
    private static final int SPEED_INCREASE_INTERVAL = 10000;
    private static final int SPEED_INCREMENT = 200;
    private static final int SHIELD_DURATION = 10000; // Shield active for 10 seconds
    private boolean isShieldActive = false; // Track shield status
    private static final int SHIELD_SPAWN_INTERVAL = 30000; // Shield spawns every 50 seconds

    private ImageView shield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        backend = new DashMode();
        MainActivity.stopMenuMusic();

        mainCar = findViewById(R.id.main_car);
        shield = findViewById(R.id.shield);

        drivingSound = MediaPlayer.create(this, R.raw.driving);
        drivingSound.setLooping(true);
        drivingSound.start();

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        laneWidth = screenWidth / 3;
        carWidth = mainCar.getLayoutParams().width;
        carHeight = mainCar.getLayoutParams().height;

        findViewById(R.id.road).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                moveCar(event.getX(), event.getY());
            }
            return true;
        });
        backend.getTime().start();//start the time of the game

        startTime = System.currentTimeMillis();

        /*spawnOncomingCars()*/
        spawnOncomingObjects();
        increaseSpeedOverTime();
        spawnShield(); // Start spawning shields

        if (!this.isGameOver) {// this is for displaying elapsed time periodically
            DT();
        }

        increaseSpeedOverTime(); // Gradual speed increase
        spawnShield();
    }

    private void moveCar(float x, float y) {
        float targetX, targetY;

        if (x < laneWidth) {
            targetX = (laneWidth - carWidth) / 2f;
        } else if (x < laneWidth * 2) {
            targetX = laneWidth + (laneWidth - carWidth) / 2f;
        } else {
            targetX = 2 * laneWidth + (laneWidth - carWidth) / 2f;
        }

        targetY = Math.min(Math.max(y - carHeight / 2f, 0), screenHeight - carHeight);
        mainCar.animate().x(targetX).y(targetY).setDuration(300).start();
    }

    private void spawnOncomingObjects() {
        handler.postDelayed(() -> {
            if (!isGameOver) {
                createOncomingObject();
                spawnOncomingObjects(); // Recursive spawning
            }
        }, 2000);
    }

    private void createOncomingObject() {
        boolean spawnCar = random.nextBoolean(); // Randomly decide to spawn a car or hole
        final ImageView oncomingObject = new ImageView(this);

        if (spawnCar) {
            oncomingObject.setImageResource(R.drawable.oncoming_car);
        } else {
            oncomingObject.setImageResource(R.drawable.hole);
        }

        oncomingObject.setLayoutParams(new RelativeLayout.LayoutParams(carWidth, carHeight));

        int lane = random.nextInt(3);
        float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
        oncomingObject.setX(startX);
        oncomingObject.setY(-200);

        RelativeLayout road = findViewById(R.id.road);
        road.addView(oncomingObject);

        oncomingObject.animate()
                .translationY(screenHeight)
                .setDuration(carSpeed)
                .withEndAction(() -> road.removeView(oncomingObject))
                .start();

        checkCollision(oncomingObject, spawnCar);
    }

    private void checkCollision(ImageView oncomingObject, boolean isCar) {
        handler.postDelayed(() -> {
            if (!isGameOver && isCollision(mainCar, oncomingObject)) {
                if (!isCar && !isShieldActive) {
                    gameOver();
                } else if (isCar && !isShieldActive) {
                    gameOver();
                }
            } else if (!isGameOver) {
                checkCollision(oncomingObject, isCar);
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

    private void spawnShield() {
        handler.postDelayed(() -> {
            if (!isGameOver) {
                int lane = random.nextInt(3);
                float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
                shield.setX(startX);
                shield.setY(-200);
                shield.setVisibility(View.VISIBLE);

                shield.animate()
                        .translationY(screenHeight)
                        .setDuration(carSpeed * 3)
                        .withEndAction(() -> shield.setVisibility(View.GONE))
                        .start();

                checkShieldCollision();
                spawnShield(); // Schedule next shield spawn
            }
        }, SHIELD_SPAWN_INTERVAL);
    }

    private void checkShieldCollision() {
        handler.postDelayed(() -> {
            if (!isGameOver && isCollision(mainCar, shield) && shield.getVisibility() == View.VISIBLE) {
                activateShield();
            } else if (!isGameOver) {
                checkShieldCollision();
            }
        }, 50);
    }

    private void DT() {
        if (backend.getTime().elapsed() % 10 == 0) {
            displayTime();
        }
    }

    private void activateShield() {
        displayTime();

        isShieldActive = true;
        mainCar.setImageResource(R.drawable.main_car_shield); // Change to shielded car
        shield.setVisibility(View.GONE); // Hide shield

        handler.postDelayed(() -> {
            isShieldActive = false;
            mainCar.setImageResource(R.drawable.main_car); // Revert to normal car
        }, SHIELD_DURATION);
    }

    private void gameOver() {
        isGameOver = true;
        long elapsedTime = (long) backend.getTime().elapsed();

        Intent intent = new Intent(DashModeActivity.this, GameOverActivityDash.class);
        intent.putExtra("SCORE", elapsedTime);
        startActivity(intent);
        finish();
    }

    private void increaseSpeedOverTime() {
        handler.postDelayed(() -> {
            if (!isGameOver) {
                carSpeed = Math.max(carSpeed - SPEED_INCREMENT, 500);
                increaseSpeedOverTime();
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

    private void displayTime() {
        Toast.makeText(this, backend.getTime().elapsedhms(), Toast.LENGTH_SHORT).show();//check time again
    }
}
