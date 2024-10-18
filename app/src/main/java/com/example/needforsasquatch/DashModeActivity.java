package com.example.needforsasquatch;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class DashModeActivity extends AppCompatActivity {

    private ImageView mainCar;
    private ImageView[] oncomingCars = new ImageView[3];  // One car per lane
    private Handler handler = new Handler();
    private Random random = new Random();
    private MediaPlayer drivingSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);

        // Stop main menu music and start driving sound
        if (MainActivity.mediaPlayer != null) {
            MainActivity.mediaPlayer.stop();
            MainActivity.mediaPlayer.release();
        }

        drivingSound = MediaPlayer.create(this, R.raw.driving);
        drivingSound.setLooping(true);
        drivingSound.start();

        // Initialize views
        mainCar = findViewById(R.id.main_car);
        oncomingCars[0] = findViewById(R.id.oncoming_car_left);
        oncomingCars[1] = findViewById(R.id.oncoming_car_middle);
        oncomingCars[2] = findViewById(R.id.oncoming_car_right);

        // Start spawning cars
        spawnOncomingCars();
    }

    private void spawnOncomingCars() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int lane = random.nextInt(3); // Random lane
                ImageView selectedCar = oncomingCars[lane];

                selectedCar.setY(-selectedCar.getHeight());  // Start from top
                selectedCar.setVisibility(View.VISIBLE);
                moveCarToLane(selectedCar, lane);

                // Recursively spawn more cars
                spawnOncomingCars();
            }
        }, 2000);  // Every 2 seconds
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerId = event.getPointerId(0);
        int pointerIndex = event.findPointerIndex(pointerId);
        // Get the pointer's current position
        float x = event.getX(pointerIndex);
        float y = event.getY(pointerIndex);
        System.out.println("X:"+x);
        System.out.println("Y:"+y);
        return true;
    }
    private void moveCarToLane(ImageView car, int lane) {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int laneWidth = screenWidth / 3;

        // Position the car in the center of its lane
        car.setX(lane * laneWidth + (laneWidth - car.getWidth()) / 2);

        car.animate()
                .translationY(getResources().getDisplayMetrics().heightPixels)  // Move to bottom
                .setDuration(3000)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        car.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drivingSound != null) {
            drivingSound.stop();
            drivingSound.release();
        }
    }
}
