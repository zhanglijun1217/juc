package threadPool;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 可调度的线程池
 * @author 夸克
 * @create 2018/7/14 17:37
 */
public class NewScheduledThreadPoolTest {

    public static void main(String[] args) {
        int a = 10;
        // newScheduledThreadPool 返回值为ScheduledExecutorService接口
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

        for (int i=0 ;i<a;i++) {
            scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    // 100以内的随机数
                    int num = new Random().nextInt(100);
                    System.out.println(num);
                }
            }, 100, TimeUnit.MILLISECONDS);
        }

        scheduledExecutorService.shutdown();

    }
}
