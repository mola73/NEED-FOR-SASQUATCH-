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

//import BackendInfo;
import BackendInfo.Mode.RunawayMode;

public class RunawayModeActivity extends AppCompatActivity {
    private RunawayMode backend;

    // private long endScore;
    //private long timeElapsed;

    private ImageView mainCar;
    private Handler handler = new Handler();

    private MediaPlayer sirenSound;
    private int screenWidth, screenHeight, laneWidth, carWidth, carHeight;

    private boolean isGameOver = false;
    private int carSpeed = 3000;
    private static final int SPEED_INCREASE_INTERVAL = 10000;
    private static final int SPEED_INCREMENT = 200;
    private ImageView nitro;
    private static final int NITRO_SPAWN_INTERVAL = 30000;
    private boolean isNitroActive = false; // Track shield status
    private static final int NITRO_DURATION = 10000; // Shield active for 10 seconds
    private boolean isShieldActive = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runaway);
        mainCar = findViewById(R.id.main_car);
        nitro = findViewById(R.id.shield);//i am not sure if this is correct
        backend = new RunawayMode(mainCar, 5);//first checkpoint  happens after 20 seconds.
        MainActivity.stopMenuMusic();
        configuration();


        findViewById(R.id.road).setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                moveCar(event.getX(), event.getY());
            }
            return true;
        });

        backend.getTime().start(); // NEW ADDITION start the time for the game

        spawnOncomingCars();
        increaseSpeedOverTime();
        checkCheckpoint();
        spawnNitro();
    }

    private void configuration() {
        sirenSound = MediaPlayer.create(this, R.raw.sirens);
        sirenSound.setLooping(true);
        sirenSound.start();

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        laneWidth = screenWidth / 3;
        carWidth = backend.getCar().getMainCar().getLayoutParams().width;
        carHeight = backend.getCar().getMainCar().getLayoutParams().height;

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
        backend.getCar().getMainCar().animate().x(targetX).y(targetY).setDuration(300).start();
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
        oncomingCar.setImageResource(R.drawable.roncoming_car);
        oncomingCar.setLayoutParams(new RelativeLayout.LayoutParams(carWidth, carHeight));

        int lane = backend.getRandom().nextInt(3);
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

    public void checkCheckpoint() {
        handler.postDelayed(() -> {
            if (backend.getCheckpoint().getCheckpointcount() == 11) {
                gameOver();
            }
            if (backend.getTime().elapsed() >= backend.getCheckpoint().getLocation() && !isGameOver) {
                checkpointNotification();
                backend.getCheckpoint().reached();
                backend.getCheckpoint().update();
            }
            if (!isGameOver) {
                checkCheckpoint();
            }
        }, 50);

    }

    public void checkpointNotification() {
        Toast.makeText(this, String.format("checkpoint %d has been passed", this.backend.getCheckpoint().getCheckpointcount()), Toast.LENGTH_LONG).show();

    }

    private void checkCollision(ImageView oncomingCar) {
        handler.postDelayed(() -> {
            if (!isGameOver && isCollision(mainCar, oncomingCar)) {

                gameOver();

            } else if (!isGameOver && backend.getCheckpoint().getCheckpointcount()==11) {
                Win();
            } else if (!isGameOver) {
                checkCollision(oncomingCar);
            }
        }, 50);
    }

    public void displayLives() {//this only works when the game is over
        Toast.makeText(this, String.format("You have  %d lives left ", this.backend.getLifecount()), Toast.LENGTH_SHORT).show();
    }

    private boolean isCollision(View v1, View v2) {
        int[] pos1 = new int[2];
        int[] pos2 = new int[2];
        v1.getLocationOnScreen(pos1);
        v2.getLocationOnScreen(pos2);

        return !(pos1[0] + v1.getWidth() < pos2[0] || pos1[0] > pos2[0] + v2.getWidth() ||
                pos1[1] + v1.getHeight() < pos2[1] || pos1[1] > pos2[1] + v2.getHeight());
    }

    private void spawnNitro() {
        handler.postDelayed(() -> {
            if (!isGameOver) {
                int lane = backend.getRandom().nextInt(3);
                float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
                nitro.setX(startX);
                nitro.setY(-200);
                nitro.setVisibility(View.VISIBLE);

                nitro.animate()
                        .translationY(screenHeight)
                        .setDuration(carSpeed * 3)
                        .withEndAction(() -> nitro.setVisibility(View.GONE))
                        .start();

                checkShieldCollision();
                spawnNitro(); // Schedule next shield spawn
            }
        }, NITRO_SPAWN_INTERVAL);
    }

    private void checkShieldCollision() {
        handler.postDelayed(() -> {
            if (!isGameOver && isCollision(backend.getCar().getMainCar(), nitro) && nitro.getVisibility() == View.VISIBLE) {
                activateNitro();
            } else if (!isGameOver) {
                checkShieldCollision();
            }
        }, 50);
    }


    private void activateNitro() {
        // displayTime();

        backend.getCheckpoint().update();
        boostcheckpoint();


        isNitroActive = true;
        backend.getCar().getMainCar().setImageResource(R.drawable.runaway_car_nitro); // Change to shielded car

        nitro.setVisibility(View.GONE); // Hide shield


        handler.postDelayed(() -> {
            isNitroActive = false;
            backend.getCar().getMainCar().setImageResource(R.drawable.runaway_car); // Revert to normal car
        }, NITRO_DURATION);
    }

    public void boostcheckpoint() {
        String a = String.format("You have been boosted to checkpoint %d", backend.getCheckpoint().getCheckpointcount());
        Toast.makeText(this, a, Toast.LENGTH_SHORT).show();
    }

    private void Win() {
        long elapsedDistance = (long) backend.getTime().elapsed();


        Intent intent = new Intent(RunawayModeActivity.this, WinActivity.class);
        intent.putExtra("SCORE", elapsedDistance);
        startActivity(intent);
        finish();
    }

    private void gameOver() {


        isGameOver = true;
        long elapsedDistance = (long) backend.getTime().elapsed();


        Intent intent = new Intent(RunawayModeActivity.this, GameOverActivity.class);
        intent.putExtra("SCORE", elapsedDistance);
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
        if (sirenSound != null) {
            sirenSound.release();
        }
    }

    private void displayTime() {
        Toast.makeText(this, backend.getTime().elapsed(), Toast.LENGTH_SHORT).show();//check time again
    }


}
