package com.example.needforsasquatch;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class DestructionModeActivity extends AppCompatActivity {

    private ImageView mainCar;
    private Handler handler = new Handler();
    private Random random = new Random();
    private MediaPlayer drivingSound;
    private MediaPlayer crashingSound;
    private int screenWidth, laneWidth, carWidth;
    private long startTime = 60;
    private long decreaseTime = 10;
    private long increaseTime = 15;
    private boolean isGameOver = false;
    private int carSpeed = 3000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);//later you should make an activity destruction

        MainActivity.stopMenuMusic();  // Stop menu music

        mainCar = findViewById(R.id.main_car); //later also remember to change car

        //Initialize driving sound
        drivingSound = MediaPlayer.create(this, R.raw.driving);
        drivingSound.setLooping(true);
        drivingSound.start();

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        laneWidth = screenWidth/3;
        carWidth = mainCar.getLayoutParams().width;

        findViewById(R.id.road).setOnTouchListener((v, event)->{
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                moveCarToLane(event.getX());
            }
            return true;
        });
    }

    private void moveCarToLane(float x){
        float targetX;
        if(x < laneWidth) {//how did he arrive to these calculations???
            targetX = (laneWidth - carWidth)/2f; //outer Left
        }else if (x < laneWidth * 2) {
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
                mainCar.getLayoutParams().width, mainCar.getLayoutParams().height
        ));

        int lane = random.nextInt(3);
        float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
        oncomingCar.setX(startX);
        oncomingCar.setY(-200);//starts outside of the screen

        RelativeLayout road = findViewById(R.id.road);
        road.addView(oncomingCar);

        oncomingCar.animate()
                .translationY(getResources().getDisplayMetrics().heightPixels)
                .setDuration(carSpeed)  // Use current speed
                .withEndAction(() -> road.removeView(oncomingCar))
                .start();

        checkCollision(oncomingCar);
    }

    private void checkCollision(ImageView oncomingCar){
        handler.postDelayed(() -> {
            if(!isGameOver && isCollision(mainCar, oncomingCar)){
                gameOver();
            }else if(!isGameOver){
                checkCollision(oncomingCar);
            }
        }, 50);
    }



}
