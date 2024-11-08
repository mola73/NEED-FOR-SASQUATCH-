package com.example.needforsasquatch;

import android.content.Intent;
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


public class DestructionModeActivity extends AppCompatActivity {

    private ImageView mainCar;
    private Handler handler = new Handler();
    private Random random = new Random();
    private MediaPlayer drivingSound;
    private MediaPlayer crashingSound;
    private int screenWidth, laneWidth, carWidth, screenHeight, carHeight;
    private long endTime = 0;//milliseconds
    private long decreaseTime = 1000;//milliseconds
    private long increaseTime = 1500;//milliseconds
    private long totalTime;
    private boolean isGameOver = false;
    private int carSpeed = 2000;
    TextView clock; //FOR SOME GODFORSAKEN REASON THIS BREAKS THE MODE AND MAKES IT GO BACK TO MAIN SCREEN

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destruction);//later you should make an activity destruction

        MainActivity.stopMenuMusic();  // Stop menu music

        mainCar = findViewById(R.id.main_car); //later also remember to change car

        //Initialize driving sound
        drivingSound = MediaPlayer.create(this, R.raw.driving);
        drivingSound.setLooping(true);
        drivingSound.start();

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        laneWidth = screenWidth/3;
        carWidth = mainCar.getLayoutParams().width;
        carHeight = mainCar.getLayoutParams().height;

        //clock = (TextView) findViewById(R.id.timer);


        findViewById(R.id.destruction).setOnTouchListener((v, event)->{
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                moveCar(event.getX(), event.getY());
            }
            return true;
        });



        endTime = (System.currentTimeMillis()/1000) + 60;
        spawnOncomingCars();

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
       checkTimeEnd();

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

        RelativeLayout road = findViewById(R.id.destruction);
        road.addView(oncomingCar);

        oncomingCar.animate()
                .translationY(getResources().getDisplayMetrics().heightPixels)
                .setDuration(carSpeed)  // Use current speed
                .withEndAction(() -> road.removeView(oncomingCar))
                .start();

        checkCollision(oncomingCar);
    }

    private void checkCollision(ImageView oncomingCar){
        RelativeLayout road = findViewById(R.id.destruction);
        handler.postDelayed(() -> {
            if(oncomingCar.getX() < road.getBottom()){//This is for when the oncoming cars get away

            }

            if(isCollision(mainCar, oncomingCar)){
                //oncomingCar.animate()
                //.withEndAction(() -> road.removeView(oncomingCar));//TODO call this ".withEndAction(() -> road.removeView(oncomingCar))" and increase time by 15
                oncomingCar.setImageDrawable(null);
                endTime = endTime + 1000;
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
        if(endTime == (System.currentTimeMillis()/1000))  {//putting this here is a bit messy, but I am not sure where else to put
            this.gameOver();
        }
    }

    private void checkTimeEnd(){

    }

    private void gameOver(){
        isGameOver = true;

        Intent intent = new Intent(DestructionModeActivity.this, GameOverActivity.class);
        intent.putExtra("SCORE", totalTime);
        startActivity(intent);
        finish();
    }
}
