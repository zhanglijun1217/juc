package threadPool;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池拒绝策略demo
 *   如果满足下列条件中的任意一个，继续向线程池中提交新的任务，那么线程池会调用拒绝策略
 *   也就是内部的RejectedExecutionHandler对象的rejectedExecution方法 表示拒绝这些新提交的任务
 *   （1）线程池处于SHUTDOWN状态时
 *   （2）线程池中所有的线程都处于运行状态，并且阻塞队列已满
 * @author 夸克
 * @date 09/10/2018 00:16
 */
public class ThreadPoolExecutorRejectNewTaskDemo {

    /**
     * 线程池的最大容量
     */
    private static final int MAX_POOL_SIZE = 3;
    /**
     * 阻塞队列的容量
     */
    private static final int QUEUE_CAPACITY = 2;
    /**
     * 非核心线程处于空闲状态的最长时间
     */
    private static final int KEEP_ALIVE_TIME = 1;
    /**
     * 线程池对象
     */
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(MAX_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS, new LinkedBlockingDeque<>(QUEUE_CAPACITY), new MyThreadFactory("task-reject"));

    public static void main(String[] args) {
//        shutdownThreadPoolToRejectNewTask();
        fullQueueThreadPoolRejectNewTask();
    }

    /**
     * 模拟线程池被关闭之后， 继续向其中提交新任务被拒绝的场景
     */
    private static void shutdownThreadPoolToRejectNewTask() {
        MyRunnable runnable = new MyRunnable();

        // 先提交 MAX_POOL_SIZE - 1个任务，此时线程池未满
        for (int i=0; i < MAX_POOL_SIZE - 1; i++) {
            System.out.println("提交任务 " + i);
            threadPool.submit(runnable);
        }
        // 在线程池未满的情况下关闭线程池
        threadPool.shutdown();
        if (threadPool.isShutdown()) {
            System.out.println("提交任务" + MAX_POOL_SIZE);
            // 在线程池未满 但却关闭的情况下去提交任务 此时会拒绝
            threadPool.submit(runnable);
        }
    }

    /**
     * 模拟线程池中线程数都在运行并且阻塞队列已满
     */
    private static void fullQueueThreadPoolRejectNewTask() {
        MyRunnable myRunnable = new MyRunnable();

        // 提交 MAX_POOL_SIZE + QUEUE_SIZE 个任务 使得线程池线程都在执行任务并且阻塞队列已满
        for (int i = 0; i < MAX_POOL_SIZE + QUEUE_CAPACITY; i++) {
            System.out.println("提交任务 " + i);
            threadPool.submit(myRunnable);
        }

        // 此时再去往其中添加 任务
        if (threadPool.getActiveCount() == MAX_POOL_SIZE && threadPool.getQueue().size() == QUEUE_CAPACITY) {
            threadPool.submit(myRunnable);
        }
    }


    /**
     * 自定义线程工厂类
     */
    private static class MyThreadFactory implements ThreadFactory {
        /**
         * namePrefix --> 线程名字中的计数
         */
        private static HashMap<String, AtomicInteger> THREAD_ID_TABLE = new HashMap<>();
        /**
         * 线程名称前缀
         */
        private String namePrefix;
        /**
         * 是否后台线程
         */
        private boolean isDamon;
        public MyThreadFactory(String namePrefix) {
            this(namePrefix, true);
        }

        public MyThreadFactory(String namePrefix, boolean isDamon) {
            this.namePrefix = namePrefix;
            this.isDamon = isDamon;
        }


        @Override
        public Thread newThread(Runnable r) {
            String threadName = namePrefix + "-" +generateThreadId(this.namePrefix);
            Thread thread = new Thread(r, threadName);
            thread.setDaemon(this.isDamon);
            System.out.println("创建线程" + threadName);
            return thread;
        }

        private static synchronized int generateThreadId(String namePrefix) {

            if (!THREAD_ID_TABLE.containsKey(namePrefix)) {
                THREAD_ID_TABLE.put(namePrefix, new AtomicInteger(0));
            }
            return THREAD_ID_TABLE.get(namePrefix).getAndIncrement();
        }
    }

    /**
     * 向线程池提交的任务
     */
    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
