package BackendInfo;

public class Checkpoint {
    /*
     A. There are five checkpoints.
B. One checkpoint should be around 1 minute of play time.

     */
    private Boolean hasArrived;
    private int location;
    private int checkpointcount;

    public Boolean getHasArrived() {
        return hasArrived;
    }

    public void setHasArrived(Boolean hasArrived) {
        this.hasArrived = hasArrived;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public Checkpoint(Boolean hasArrived, int xCoordinate) {
        this.hasArrived = hasArrived;
        this.location = xCoordinate;
        this.checkpointcount = 1;
    }

    public int getCheckpointcount() {
        return checkpointcount;
    }

    public void incrementCheckpointcount() {
        this.checkpointcount = checkpointcount + 1;
    }

    public Checkpoint(int time) {
        this.hasArrived = false;
        this.location = time;
        this.checkpointcount = 1;
    }

    public void reached() {
        this.hasArrived = true;

    }
    public boolean hasWon(){
        if(this.checkpointcount==11){
            return true;
        }
        return false;
    }

    public void update() {
        if (this.hasArrived) {
            incrementCheckpointcount();
            this.setLocation(this.location * 2);
            this.hasArrived = false;
        }
    }

    public Checkpoint newCheckpoint(int newtime) {
        return new Checkpoint(newtime);
    }

    public String announcepass() {


        return String.format("The vehicle has passed checkpoint %d", this.checkpointcount);
    }
}
