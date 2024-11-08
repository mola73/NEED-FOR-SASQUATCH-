package BackendInfo.Mode;

import BackendInfo.Vehicle.Stopwatch;

public class Mode {
    private Stopwatch time;
    private int score;


    public Mode() {
this.time=new Stopwatch();
this.score=0;
    }

    public Stopwatch getTime() {
        return time ;
    }




    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Mode(int score, Stopwatch time) {
        this.score = score;
        this.time=time;
    }





}
