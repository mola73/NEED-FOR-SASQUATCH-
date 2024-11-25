package BackendInfo.Mode;

import BackendInfo.Vehicle.DashVehicle;
import BackendInfo.Vehicle.Stopwatch;

public class DestructionMode extends Mode {

    private Stopwatch time;
    private int score;
    private DashVehicle car;

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

    public DashVehicle getCar() {
        return car;
    }

    public void setCar(DashVehicle car) {
        this.car = car;
    }

    public DestructionMode(double endTime){
        super();
        this.time=new Stopwatch(endTime);

    }

    public DestructionMode(){
        super();
    }
}
