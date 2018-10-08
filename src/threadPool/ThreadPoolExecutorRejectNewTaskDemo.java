package threadPool;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池拒绝策略demo
 * @author 夸克
 * @date 09/10/2018 00:16
 */
public class ThreadPoolExecutorRejectNewTaskDemo {

    // 线程池的最大容量
    private static final int MAX_POOL_SIZE = 3;
    // 阻塞队列的容量
    private static final int QUEUE_CAPACITY = 2;
    // 非核心线程处于空闲状态的最长时间
    private static final int KEEP_ALIVE_TIME = 1;
    // 线程池对象
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(MAX_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(QUEUE_CAPACITY), new MyThreadFactory());

    private static class MyThreadFactory implements ThreadFactory {
        static int threadNumber = 0;

        @Override
        public Thread newThread(Runnable r) {
            String threadName = "thread-" + threadNumber++;
            System.out.println("创建线程" + threadName);
            return new Thread(r, threadName);
        }
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
