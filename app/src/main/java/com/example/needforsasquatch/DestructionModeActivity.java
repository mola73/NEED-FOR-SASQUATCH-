package com.example.needforsasquatch;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;
import android.os.CountDownTimer;
import BackendInfo.Mode.DestructionMode;


public class DestructionModeActivity extends AppCompatActivity {

    private DestructionMode backend;
    private ImageView mainCar;
    private RelativeLayout road;
    private Handler handler = new Handler();
    private Random random = new Random();
    private MediaPlayer drivingSound;
    private MediaPlayer crashingSound;
    private int screenWidth, laneWidth, carWidth, screenHeight, carHeight;
    private long endTime = 0;//milliseconds
    private long startTime= 0;
    private long totalTime;
    private boolean isGameOver = false;
    private int carSpeed = 625;
    //private boolean oncomingCarExists = false;
    private ImageView timeBoost;
    private TextView counterDisplay;//IF IT BREAKS, IT IS HERE
    private int counter = 0;
    private int TIME_BOOST_SPAWN_INTERVAL = 40000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destruction);//later you should make an activity destruction
        counterDisplay = findViewById(R.id.counter);
        counterDisplay.setVisibility(View.VISIBLE);

        MainActivity.stopMenuMusic();  // Stop menu music
        endTime = (System.currentTimeMillis()/1000) + 60;//converting it to seconds 
        startTime = System.currentTimeMillis()/1000;
       backend = new DestructionMode(endTime);
        //backend.getTime().setStartTime(startTime);
        //backend.getTime().start();
        counterDisplay.setText(String.valueOf(counter));

        mainCar = findViewById(R.id.main_car); //later also remember to change car
        timeBoost = findViewById(R.id.time_boost);
        road = findViewById(R.id.destruction);
        AnimationDrawable roadAnimation = (AnimationDrawable) road.getBackground();
        roadAnimation.setEnterFadeDuration(10);
        roadAnimation.setExitFadeDuration(10);

        //Initialize driving sound
        drivingSound = MediaPlayer.create(this, R.raw.driving);
        drivingSound.setLooping(true);
        drivingSound.start();

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        laneWidth = screenWidth/3;
        carWidth = mainCar.getLayoutParams().width;
        carHeight = mainCar.getLayoutParams().height;

        findViewById(R.id.destruction).setOnTouchListener((v, event)->{
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                moveCar(event.getX(), event.getY());
            }
            return true;
        });

        spawnOncomingCars();
        spawnTimeIncrease();
    }

    private void checkGameOver(){
        if( (endTime == System.currentTimeMillis()/1000))  {//putting this here is a bit messy, but I am not sure where else to put
            this.gameOver();
        }else if(counter >= 5){
            this.youWin();
        }
    }

    private void moveCar(float x, float y){
        float targetX, targetY;

        if(x < laneWidth) {
            targetX = (laneWidth - carWidth)/2f; //outer Left
        }else if (x < laneWidth * 2) {
            targetX = laneWidth + (laneWidth - carWidth) / 2f;  // Middle lane
        } else {
            targetX = 2 * laneWidth + (laneWidth - carWidth) / 2f;  // Right lane
        }

        targetY = Math.min(Math.max(y - carHeight / 2f, 0), screenHeight - carHeight);
        mainCar.animate().x(targetX).y(targetY).setDuration(300).start();
    }

    private void spawnOncomingCars() {
        this.checkGameOver();

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
        //Toast.makeText(this, oncomingCar.getY(), Toast.LENGTH_SHORT).show();

        RelativeLayout road = findViewById(R.id.destruction);
        road.addView(oncomingCar);

        oncomingCar.animate()
                .translationY(getResources().getDisplayMetrics().heightPixels)
                .setDuration(carSpeed)
                .withEndAction(() -> road.removeView(oncomingCar))
                .start();

        checkCollision(oncomingCar);
    }

    private void checkCollision(ImageView oncomingCar){
        handler.postDelayed(() -> {
            if(isCollision(mainCar, oncomingCar)){
                oncomingCar.setImageDrawable(null);
                //endTime = endTime + 10;
                counter = counter + 1;
                counterDisplay.setText(String.valueOf(counter));//apparently, Android Studio can't use an intenger as a string
                backend.getTime().setEndTime(endTime);
                displayTime();
            }else if(!isGameOver){
                checkCollision(oncomingCar);
            }
        }, 50);
    }

    private boolean isCollision(View v1, View v2){
        int[] pos1 = new int[2];
        int[] pos2 = new int[2];
        v1.getLocationOnScreen(pos1);
        v2.getLocationOnScreen(pos2);
        return !(pos1[0] + v1.getWidth() < pos2[0] || pos1[0] > pos2[0] + v2.getWidth() ||
                pos1[1] + v1.getHeight() < pos2[1] || pos1[1] > pos2[1] + v2.getHeight());
    }

    private void spawnTimeIncrease(){
            handler.postDelayed(() -> {
                if (!isGameOver) {
                    int lane = random.nextInt(3);
                    float startX = lane * laneWidth + (laneWidth - carWidth) / 2f;
                    timeBoost.setX(startX);
                    timeBoost.setY(-200);
                    timeBoost.setVisibility(View.VISIBLE);

                    timeBoost.animate()
                            .translationY(screenHeight)
                            .setDuration(carSpeed * 3)
                            .withEndAction(() -> timeBoost.setVisibility(View.GONE))
                            .start();

                    checkTimeCollision();
                    spawnTimeIncrease(); // Schedule next shield spawn
                }
            }, TIME_BOOST_SPAWN_INTERVAL);

    }

    private void checkTimeCollision(){
        handler.postDelayed(() -> {
            if(!isGameOver && isCollision(mainCar, timeBoost) && timeBoost.getVisibility() == View.VISIBLE){
                activateTimeBoost();
            }else if (!isGameOver){
                checkTimeCollision();
            }
        }, 50);
    }

    private void activateTimeBoost(){
        endTime = endTime + 10;
        displayTime();
    }

    private void gameOver(){
        isGameOver = true;
        Intent intent = new Intent(DestructionModeActivity.this, GameOverActivity.class);
        intent.putExtra("SCORE", counter);
        startActivity(intent);
        finish();
    }

    private void youWin(){
        isGameOver = true;
        Intent intent = new Intent(DestructionModeActivity.this, WinActivity.class);
        startActivity(intent);
        finish();
    }


    private void displayTime(){
            Toast.makeText(this, String.valueOf((int) (endTime - (System.currentTimeMillis()/1000))), Toast.LENGTH_SHORT).show();
    }


}
