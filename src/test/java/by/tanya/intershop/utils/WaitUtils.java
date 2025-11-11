package by.tanya.intershop.utils;

import java.time.Duration;

public class WaitUtils {

    public static void waitFor(Duration duration) {
        try {
            synchronized (WaitUtils.class) {
                WaitUtils.class.wait(duration.toMillis());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
