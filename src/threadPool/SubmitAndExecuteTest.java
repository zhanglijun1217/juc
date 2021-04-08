package threadPool;

import java.util.concurrent.*;

/**
 * 测试线程池 submit、execute方法 在异常时的处理
 * https://www.cnblogs.com/ncy1/articles/11629933.html
 */
public class SubmitAndExecuteTest {
    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception{
//
        Runnable task = () -> {int i = 1/0; System.out.println("task被调用了");};


        /**
         *  执行一个 抛异常 的任务
         */
//        executorService.submit(task);
////        System.out.println("submit的结果: " + submit.get()); // 这里不去调用get上面submit之后不会输出错误
//
//        executorService.execute(task); // 会输出错误日志  因为execute会抛出异常 而不是包在futureTask的返回值中


        /**
         * 2. 线程池去注册线程工厂（UncaughtExceptionHandler）
         */
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                //给线程注册
                thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        System.err.println("❌ 线程：" + Thread.currentThread().getName() + "发生异常:" + e.getMessage());
                    }
                });
                return thread;
            }
        };
        ExecutorService exHandlerES = Executors.newFixedThreadPool(1, threadFactory);
        exHandlerES.submit(task); // submit执行 因为内部并没有把异常抛出，所以即使注册了setUncaughtExceptionHandler也不生效。
        exHandlerES.execute(task); // execute执行 内部抛出了异常， 所以会应用线程绑定的uncaughtExceptionHandler
        new CountDownLatch(1).await();
    }
}
