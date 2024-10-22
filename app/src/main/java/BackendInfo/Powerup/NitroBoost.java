package BackendInfo.Powerup;

import BackendInfo.Clock;
/*
Boost is available only in Destruction Mode.
The first time, the boost will have a 90% chance to appear, the second, it will decrease to 80% and so on, decreasing 10% each time.

 */
public class NitroBoost extends Powerup{
    public NitroBoost(Clock timer, float x, float y) {
        super(timer, x, y);
    }
    public NitroBoost getnewBoost(Clock timer,float x,float y){
        return new NitroBoost(timer,x,y);
    }
}



