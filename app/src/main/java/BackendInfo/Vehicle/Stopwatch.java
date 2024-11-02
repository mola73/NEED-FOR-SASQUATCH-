package BackendInfo.Vehicle;

public class  Stopwatch {

    private double starttime;
    private boolean started;
    private boolean stopped;
    private double elapsedtime;

    public double getStarttime() {
        return starttime;
    }

    public Stopwatch() {

        this.elapsedtime = 0;
        this.started = false;

    }

    public Stopwatch start() {  //Starts the stopwatch Has noeffect if the stopwatch is already started.
        // Does not reset the time.
        if (!this.started) {
            this.starttime = System.nanoTime();//update elapsed time and track the time the watch was started to determine isRunning method.

            this.started = true;
            this.stopped = false;
        }
        return this;
    }

    public Stopwatch stop() { // Stops the stopwatch. Has no effect if the stopwatch is already stopped.Does not reset the time.Returns this.
        if (!stopped) {
            this.elapsedtime = System.nanoTime() - this.starttime; //update elapsed time and track the time the watch was stopped to detirmine isRunning method.
            stopped = true;
            this.started = false;
        }
        return this;
    }

    public Stopwatch reset() {// Resetstheelapsedtimetozero.Neitherstartsnorstopsthe stopwatch.Returnsthis.
        this.elapsedtime = 0;
        this.starttime = 0;

        return this;
    }

    public double elapsed() { //Returns the elapsed CPU time in seconds at the time of the call.
        if (started) {
            this.elapsedtime = System.nanoTime() - this.starttime;

        }
        return this.elapsedtime / 100_000_000; //
    }

    public boolean isRunning() {
        return this.started;

    }
    public String elapsedhms(){
        int hours= (int) elapsed()/3600;
        int minutes= (int)elapsed()/60;
        int seconds=(int)elapsed() %60;
        return String.format("%d hours\n %d min.\n %d sec.\n",hours,minutes,seconds);
    }

    public String toString() {
        return String.format("ElapsedTime:%f seconds%n Running?: %b%n  ", this.elapsed()/60,this.elapsed()/ this.getStarttime());

    }
}
