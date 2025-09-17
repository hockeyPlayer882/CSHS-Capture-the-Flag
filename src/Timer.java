public class Timer{
    public int minutes;
    public int seconds;
    //target FPS of 60
    public static final int FPS = 60;
    boolean isDone=false;
    public int resetMinutes;
    public int resetSeconds;
    TimerThread t;
    /**
     * constructs a timer
     * @param minutes starting minutes
     * @param seconds starting seconds
     * @param resetMinutes number of minutes that will be set to upon reset
     * @param resetSeconds number of seconds that will be set to upon reset
     */
    public Timer(int minutes, int seconds,int resetMinutes,int resetSeconds){
        this.minutes = minutes;
        this.seconds = seconds;
        isDone=false;
        this.resetMinutes=resetMinutes;
        this.resetSeconds=resetSeconds;
    }
    public void decreaseTime(){
        seconds--;
        if(seconds < 0){
            seconds = 60;
            minutes--;
        }
    }
    public void resetTimer(){
        isDone = false;
        minutes=resetMinutes;
        seconds=resetSeconds;
    }
    public class TimerThread extends Thread{
        @Override
        public void run(){
            while(true){
                decreaseTime();
                if(seconds == 0 && minutes==0){
                    isDone=true;
                }
                try {
                    //60FPS=sleep for 1/(60*(1000)) miliseconds
                    sleep(1/(FPS*1000));
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}