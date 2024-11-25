package BackendInfo.Mode;

import android.widget.ImageView;

import java.util.Random;

import BackendInfo.Checkpoint;
import BackendInfo.Vehicle.RunawayVehicle;
import BackendInfo.Vehicle.Stopwatch;


public class RunawayMode extends Mode {
    private Stopwatch time;
    private int score;
    private Checkpoint checkpoint;
    private int lifecount;
    private Random random;
    private RunawayVehicle car;

    public int getLifecount() {
        return lifecount;
    }

    public void setLifecount(int lifecount) {
        this.lifecount = lifecount;
    }

    public Checkpoint getCheckpoint() {
        return checkpoint;
    }


    public void loselife() {
        --this.lifecount;
    }

    public boolean inDanger() {
        if (this.lifecount <= 1) {
            return true;
        }
        return false;
    }

    public Random getRandom() {
        return this.random;
    }


    public RunawayMode(ImageView mainCar, int checkpointtime) {
        super();
        this.time = new Stopwatch();
        this.car = new RunawayVehicle(mainCar);
        this.checkpoint = new Checkpoint(checkpointtime);
        this.lifecount = 3;
        this.random = new Random();
    }

    public RunawayMode(int score, Stopwatch time) {
        super(score, time);
    }

    @Override
    public Stopwatch getTime() {
        return time;
    }

    public void setTime(Stopwatch time) {
        this.time = time;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    public RunawayVehicle getCar() {
        return car;
    }

    public void setCar(RunawayVehicle car) {
        this.car = car;
    }


}
