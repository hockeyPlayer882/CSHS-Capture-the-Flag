public class Timer {
    public int minutes;
    public int seconds;
    public int miliseconds;

    // target FPS of 60
    //FIXME: should be 60 FPS
    public static int timeScalar = 1;
    public static final int FPS = 60;
    boolean isDone = false;

    public int resetMinutes;
    public int resetSeconds;
    public TimerThread t;

    /**
     * constructs a timer
     * 
     * @param minutes      starting minutes
     * @param seconds      starting seconds
     * @param resetMinutes number of minutes that will be set to upon reset
     * @param resetSeconds number of seconds that will be set to upon reset
     */
    public Timer(int minutes, int seconds, int resetMinutes, int resetSeconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        miliseconds = 60;
        isDone = false;
        this.resetMinutes = resetMinutes;
        this.resetSeconds = resetSeconds;
        t = new TimerThread();
    }

    public void decreaseTime() {
        if (!isDone) {
            miliseconds--;

            if(miliseconds == 0) {
                seconds--;
                miliseconds=60;
            }

            if (seconds < 0) {
                seconds = 59;
                minutes--;
            }
        }
    }

    public void resetTimer() {
        isDone = false;
        minutes = resetMinutes;
        seconds = resetSeconds;
    }

    public class TimerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                decreaseTime();

                if (seconds == 0 && minutes == 0)
                    isDone = true;

                // 60FPS = sleep for (1000 / FPS) miliseconds
                Utils.sleep(1000 / (FPS * timeScalar));
            }
        }
    }
}