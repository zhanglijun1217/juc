package lesson.wwj.juc.thread_status;

import java.util.concurrent.TimeUnit;

/**
 * Created by zlj on 2019/2/23.
 */
public class SleepUtils {

    public static final void second(long seconds) {
            try {
                TimeUnit.SECONDS.sleep(seconds);

            } catch (InterruptedException e) {

            }
    }
}
