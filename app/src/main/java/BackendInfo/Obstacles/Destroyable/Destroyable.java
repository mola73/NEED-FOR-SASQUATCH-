package BackendInfo.Obstacles.Destroyable;

import BackendInfo.Obstacles.Obstacle;

public class Destroyable extends Obstacle {
    public Boolean getDestoryed() {
        return isDestoryed;
    }

    public void setDestoryed(Boolean destoryed) {
        isDestoryed = destoryed;
    }

    private Boolean isDestoryed;
    public Destroyable() {
        super();
        this.isDestoryed=false;
    }
}
