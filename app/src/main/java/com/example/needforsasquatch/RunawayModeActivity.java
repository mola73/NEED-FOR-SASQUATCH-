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

import BackendInfo.DashMode;

public class RunawayModeActivity extends AppCompatActivity {
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


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runaway);

        MainActivity.stopMenuMusic();

        mainCar = findViewById(R.id.main_car);


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


    private void gameOver() {


        isGameOver = true;
        long elapsedTime =(long) backend.getTime();

        Intent intent = new Intent(RunawayModeActivity.this, GameOverActivity.class);
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
