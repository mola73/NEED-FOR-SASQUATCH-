package BackendInfo.Mode;

public class Mode {
    private double time;
    private int score;

    public Mode() {

    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Mode(int score, double time) {
        this.score = score;
        this.time=time;
    }





}
