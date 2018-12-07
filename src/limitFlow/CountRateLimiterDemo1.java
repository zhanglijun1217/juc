package limitFlow;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器限流 应用比较常用，主要用于限制总并发数，比如数据库连接池大小、线程池大小、程序访问并发数都广泛使用计数器算法
 * 只是单机简单限流、不能做到分布式限流
 * @author 夸克
 * @date 2018/12/7 17:11
 */
public class CountRateLimiterDemo1 {


    private AtomicInteger count = new AtomicInteger(0);

    public void exec() {

        if (count.get() > 5) {
            System.out.println("请求用户过多，请稍后再试。请求时间：" + System.currentTimeMillis() / 1000);
        } else {
            // count ++
            count.incrementAndGet();

            // 模拟核心逻辑
            try {
                System.out.println("线程" + Thread.currentThread().getName() + "执行任务，请求时间：" + System.currentTimeMillis());
                TimeUnit.SECONDS.sleep(10);
                System.out.println("线程" + Thread.currentThread().getName() + "执行任务完成");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // count --
                count.decrementAndGet();
            }
        }
    }

    public static void main(String[] args) {
        CountRateLimiterDemo1 countRateLimiterDemo1 = new CountRateLimiterDemo1();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new CountRunnable(countRateLimiterDemo1));
            thread.setDaemon(false);
            thread.start();
        }
    }
}

class CountRunnable implements Runnable {

    private CountRateLimiterDemo1 countRateLimiterDemo1;

    public CountRunnable(CountRateLimiterDemo1 countRateLimiterDemo1) {
        this.countRateLimiterDemo1 = countRateLimiterDemo1;
    }

    @Override
    public void run() {
        countRateLimiterDemo1.exec();
    }
}
