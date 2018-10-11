package countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 利用CountdownLatch模拟并发请求
 * 里面利用了两个CountDownLatch 来自于《java并发编程实战》
 * @author 夸克
 * @date 11/10/2018 10:33
 */
public class SimulateConcurrentRequest {

    public static void main(String[] args) {
        Runnable task = new Runnable() {
            // 埋坑
            private AtomicInteger counter = new AtomicInteger(1);

            @Override
            public void run() {
                for(int i = 0; i < 10; i++) {
                    String s = HttpClientOp.doGet("https://www.baidu.com");
                    System.out.println(System.nanoTime() + " [" + Thread.currentThread().getName() + "] iCounter = " + counter.getAndIncrement()
                     + "baidu result is " + s);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        SimulateConcurrentRequest simulateConcurrentRequest = new SimulateConcurrentRequest();
        try {
            simulateConcurrentRequest.startTaskAllInOne(5, task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * countLatch进行真正并发模拟
     * @param threadNums
     * @param task
     * @return 执行时间
     * @throws InterruptedException
     */
    public long startTaskAllInOne(int threadNums, final Runnable task) throws InterruptedException {
        // 开始门 用于所有线程都处于就绪状态
        final CountDownLatch startGate = new CountDownLatch(1);
        // 关闭门 所有的线程都访问完 用于计算时间
        final CountDownLatch endGate = new CountDownLatch(threadNums);
        for (int i=0;i<threadNums;i++) {
            Thread t = new Thread(() -> {
                try {
                    // 开始门使线程等待
                    startGate.await();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    endGate.countDown();
                }
            });

            t.start();
        }

        long startTime = System.currentTimeMillis();
        // 此时真正的去让线程执行任务
        startGate.countDown();
        // 结束门在这里等待 直到所有线程都执行完
        endGate.await();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
}
