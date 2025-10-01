import java.sql.Time;

public class Utils {
    private static final int NANOS_TO_MILLIS = 1_000_000;
    private static final int MILLIS_TO_NANOS = NANOS_TO_MILLIS;

    /**
     * Wrapper function around Thread.sleep() to hide the complexity of interacting
     * with Java's threading model. Sleeps for a specified quantity of milliseconds.
     * 
     * @param millis Quantity of milliseconds to suspend execution for.
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ie) {
            // We don't care about handling these exceptions since they're entirely
            // irrelevant for this application.
        }
    }

    public static void adaptiveFrameSleep(long millis, long start_of_frame_ns) {
        long cntr = System.nanoTime();

        long nsToWait = (millis * MILLIS_TO_NANOS) - (cntr - start_of_frame_ns);
        if (nsToWait <= 0)
            return;
            
        sleep((int)((nsToWait / NANOS_TO_MILLIS) + 0.9f));
    }
}
