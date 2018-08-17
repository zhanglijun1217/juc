package threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池学习
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了响应的速度
 *
 * 二、线程池的体系结构
 *  java.util.concurrent.Executor  负责线程的使用和调度的根接口
 *    |-- ExecutorService 子接口：线程池的主要接口
 *          |-- ThreadPoolExecutor 线程池的实现类
 *          |-- ScheduledExecutorService 子接口：负责线程的调度
 *              |-- ScheduleThreadPoolExecutor 继承ThreadPoolExecutor 实现了ScheduleExecutorService接口
 * @author 夸克
 * @create 2018/7/14 15:41
 */
public class ThreadPoolTest {

    public static void main(String[] args) {
//        ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        for (int i=0; i< 10; i++) {
//            executorService.submit(threadPoolDemo);
//        }

        // 传入Callable
        // 初始化返回值
        List<Future<String>> list = new ArrayList<>();
        for (int i=0; i< 10; i++) {
            Future<String> f = executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    int sum = 0;

                    for (int i = 0; i <= 100; i++) {
                        sum += i;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    return stringBuilder.append(Thread.currentThread().getName()).append(" " + sum).toString();
                }
            });
            list.add(f);
        }

        try {
            for (Future<String> integerFuture : list) {
                System.out.println(integerFuture.get());
            }
        } catch (Exception e) {

        }finally {
            // 线程池关闭
            executorService.shutdown();
        }




    }
}
class ThreadPoolDemo implements Runnable {

    private int i;
    @Override
    public synchronized void run() {
        while (i<100) {
            System.out.println(Thread.currentThread().getName() + " : " + i++);
        }
    }
}
