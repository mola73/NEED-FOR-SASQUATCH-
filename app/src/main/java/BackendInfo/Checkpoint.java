package BackendInfo;

public class Checkpoint {
    /*
     A. There are five checkpoints.
B. One checkpoint should be around 1 minute of play time.

     */
    private Boolean hasArrived;
    private double xCoordinate;

    public Boolean getHasArrived() {
        return hasArrived;
    }

    public void setHasArrived(Boolean hasArrived) {
        this.hasArrived = hasArrived;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Checkpoint (Boolean hasArrived, double xCoordinate) {
        this.hasArrived = hasArrived;
        this.xCoordinate = xCoordinate;
    }
    public void getAnnouncemnent(){
        System.out.println("The vehicle has passed the checkpoint");
    }
}
