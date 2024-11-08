package BackendInfo.Powerup;

import androidx.annotation.NonNull;

import BackendInfo.Clock;

public class InvincibilityPowerup extends Powerup{

    public InvincibilityPowerup() {
        super(new Clock(),10,10);
    }

    public InvincibilityPowerup(float x, float y) {
        super();
        this.setX(x);
        this.setY(y);
    }

    public void boostAnnouncement(){
        System.out.println("The vehicle is invincible for 15 seconds");
        System.out.println("The boost has been used");

    }


    @NonNull
    @Override
    public String toString() {
        return super.toString();
        // FIXME: 10/21/2024
    }
}
