package BackendInfo;

public class Score {
    private int dmScore;
    private int demScore;
    private int rmScore;

    public int getDmScore() {
        return dmScore;
    }

    public void setDmScore(int dmScore) {
        this.dmScore = dmScore;
    }

    public int getDemScore() {
        return demScore;
    }

    public void setDemScore(int demScore) {
        this.demScore = demScore;
    }

    public int getRmScore() {
        return rmScore;
    }

    public void setRmScore(int rmScore) {
        this.rmScore = rmScore;
    }

    public void announcement() {
        System.out.println("The scores should be here");
        // TODO: 10/21/2024  implement binary tree and hash table for scoreboard 
    }
}
