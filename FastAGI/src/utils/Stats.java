package utils;

/**
 * Created by andrea on 01/07/17.
 */
public class Stats {
    private static int rejectedCallsCounter = 0;

    public static void increaseRejectedCallsCounter(){
        rejectedCallsCounter++;
    }

    public static int getRejectedCallsCounter() {
        return rejectedCallsCounter;
    }
}
