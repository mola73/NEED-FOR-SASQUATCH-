package BackendInfo.Mode;

import BackendInfo.Vehicle.DashVehicle;
import BackendInfo.Vehicle.RunawayVehicle;
import BackendInfo.Vehicle.Stopwatch;


public class RunawayMode extends Mode {
    private Stopwatch time;
    private int score;
    private RunawayVehicle car;

    public RunawayMode() {
        super();
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
