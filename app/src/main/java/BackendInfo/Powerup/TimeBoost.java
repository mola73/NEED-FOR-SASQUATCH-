package BackendInfo.Powerup;

import BackendInfo.Clock;

public class TimeBoost extends Powerup {

    public TimeBoost(Clock timer, float x, float y) {
        super(timer, x, y);
    }

    public void boostAnnounce() {
        System.out.println("Time increased by 15");
        System.out.println("Boost was achieved");
    }
}
