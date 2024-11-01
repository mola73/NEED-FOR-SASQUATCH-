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
    private int screenWidth, screenHeight, laneWidth, carWidth, carHeight;
    private long startTime;
    private boolean isGameOver = false;
    private int carSpeed = 3000;
    private static final int SPEED_INCREASE_INTERVAL = 10000;
    private static final int SPEED_INCREMENT = 200;
    private static final int SHIELD_DURATION = 10000; // Shield active for 10 seconds
    private boolean isShieldActive = false; // Track shield status

    private ImageView shield;
    private static final int SHIELD_SPAWN_INTERVAL = 30000; // Shield spawns every 50 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

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

        startTime = System.currentTimeMillis();
        spawnOncomingCars();
        increaseSpeedOverTime();
        spawnShield(); // Start spawning shields
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

    private void spawnOncomingCars() {
        handler.postDelayed(() -> {
            if (!isGameOver) {
                createOncomingCar();
                spawnOncomingCars();
            }
        }, 2000);
    }
//    private void spawnBoost(){
//        // i am not exactly sure if this is write
//        //if the time is low enough to spawn a boost then it is spawned
//        handler.postDelayed(()->{
//          //  if(boosttime){
//                createOncomingBoost();
//                spawnBoost();
//            }
//        },2000);
//    }
    private void createOncomingBoost(){ //invincibility boost creation 
        // TODO: 10/30/2024  Muhammad added this for the boost
        
        final ImageView iBoost= new ImageView(this);
        iBoost.setImageResource(R.drawable.invincibility_boost);
        iBoost.setLayoutParams(new RelativeLayout.LayoutParams(mainCar.getLayoutParams().width, mainCar.getLayoutParams().height));

        int lane = random.nextInt(3);
        float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
        iBoost.setX(startX);
        iBoost.setY(-200);

        RelativeLayout road = findViewById(R.id.road);
        road.addView(iBoost);

        iBoost.animate()
                .translationY(getResources().getDisplayMetrics().heightPixels)
                .setDuration(carSpeed)  // Use current speed
                .withEndAction(() -> road.removeView(iBoost))
                .start();

        checkCollision(iBoost);
    }

    private void createOncomingCar() {
        final ImageView oncomingCar = new ImageView(this);
        oncomingCar.setImageResource(R.drawable.oncoming_car);
        oncomingCar.setLayoutParams(new RelativeLayout.LayoutParams(carWidth, carHeight));

        int lane = random.nextInt(3);
        float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
        oncomingCar.setX(startX);
        oncomingCar.setY(-200);

        RelativeLayout road = findViewById(R.id.road);
        road.addView(oncomingCar);

        oncomingCar.animate()
                .translationY(screenHeight)
                .setDuration(carSpeed)
                .withEndAction(() -> road.removeView(oncomingCar))
                .start();

        checkCollision(oncomingCar);
    }

    private void checkCollision(ImageView oncomingCar) {
        handler.postDelayed(() -> {
            if (!isGameOver && isCollision(mainCar, oncomingCar)) {
                if (!isShieldActive) {
                    gameOver();
                } // Ignore collision if shield is active
            } else if (!isGameOver) {
                checkCollision(oncomingCar);
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

    private void activateShield() {
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
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;

        Intent intent = new Intent(DashModeActivity.this, GameOverActivity.class);
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
}
