package BackendInfo;

public class Clock {
    private double time;

    // TODO: 10/31/2024  fix this clas to track time 
    public Clock(double time) {
        this.time = time;
    }

    public Clock() {
        this.time = 15;
    }

    public Clock getNewClock(double giventime) {// what time will the clock start from
        return new Clock(giventime);
    }

    private void starTime() {
        //implementation goes here
    }

    private void stopTime() {
        //implementation goes here
    }

    private void resetTime() {
        this.setTime(0);
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }
}
