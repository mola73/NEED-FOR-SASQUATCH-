package BackendInfo;

public class Sirens {
    public Boolean isFlashing;

    public void sirensSwitch() {
        if (isFlashing) {
            this.turnOff();

        } else {
            turnOff();
        }

    }

    public Boolean getFlashing() {
        return isFlashing;
    }

    public void setFlashing(Boolean flashing) {
        isFlashing = flashing;
    }

    public void turnOff() {
        this.setFlashing(false);
    }

    public void turnOn() {
        this.setFlashing(true);
    }
}
