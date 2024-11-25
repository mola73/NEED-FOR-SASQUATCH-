package BackendInfo.Vehicle;

public class DashVehicle extends Vehicle {

    public Boolean getInvincible() {
        return isInvincible;
    }

    public void setInvincible(Boolean invincible) {
        isInvincible = invincible;
    }

    private Boolean isInvincible;

    public DashVehicle() {
        super();
    }
}
