package BackendInfo.Powerup;

import androidx.annotation.NonNull;

import BackendInfo.Clock;

public class Powerup {
    private Boolean isActive;
    private Clock timer;
    private double x;//x coordinates of boost
    private double y;//y coordinates of boost
    private Boolean isVisible;
    private Boolean isUsed;

    public Powerup() {
        this.isActive = false;
        this.timer = new Clock();
        this.x = 10;
        this.y = 10;
        this.isVisible = true;
        this.isUsed = false;
    }

    public Powerup(Clock timer, float x, float y) {
        this.isActive=true;
        this.timer = timer;
        this.x = x;
        this.y = y;
        this.isVisible = false;
        this.isUsed = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

//    public Powerup getNewBoost(Clock c,float x,float y){
//        return new Powerup(c,x,y);
//    }

    public void removeBoost(){
        // TODO: 10/21/2024
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
