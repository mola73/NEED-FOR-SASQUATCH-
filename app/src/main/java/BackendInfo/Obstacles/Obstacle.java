package BackendInfo.Obstacles;

public class Obstacle {
    private double X;
    private double Y;

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public Boolean getHit() {
        return isHit;
    }

    public void setHit(Boolean hit) {
        isHit = hit;
    }

    private Boolean isHit;

    public Obstacle(double x, double y, Boolean isHit) {
        X = x;
        Y = y;
        this.isHit = isHit;
    }

    public Obstacle() {
        this.X=0;
        this.Y=0;
        this.isHit=false;
    }
    public Obstacle getNewObstacle(){
        return new Obstacle();
    }
    public void getLocation(){
        System.out.printf("X: %f, Y:%f", this.getX(),this.getY());
    }
}
