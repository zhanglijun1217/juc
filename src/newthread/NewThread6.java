package newthread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by zlj on 2018/5/31
 * 线程池创建线程
 */
public class NewThread6 {

    public static void main(String[] args) {

        // 创建十个定长的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // 提交一百个 线程执行的任务
        for (int i=0; i<100; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }

        // 线程池的停止销毁 否则程序一直会运行
        threadPool.shutdown();
        // 注意shutdownNow方法和shutdown方法的区别
        threadPool.shutdownNow();
    }
}
