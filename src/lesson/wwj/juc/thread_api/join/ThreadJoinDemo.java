package lesson.wwj.juc.thread_api.join;

/**
 * Thread.join方法的一个小demo ：
 *  假设有四台服务器，每个线程要对每台服务器采集信息，比如不同的服务器采集需要不同的时间，
 *  这里要求主线程去记录时间的时候，必须等待每个线程采集信息完毕
 *
 * @author 夸克
 * @date 2019/2/20 00:29
 */
public class ThreadJoinDemo {

    public static void main(String[] args) {

        // 模拟三个服务器
        long begin = System.currentTimeMillis();
        Thread thread1 = new Thread(new CaptureRunnable("m1", 1_000));
        Thread thread2 = new Thread(new CaptureRunnable("m2", 2_000));
        Thread thread3 = new Thread(new CaptureRunnable("m3", 3_000));

        thread1.start();
        thread2.start();
        thread3.start();

        // 这里必须调用join 才能保证每个线程执行完毕
        try {
            thread1.join();
            thread2.join();
            thread3.join();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 因为是每个服务器 并行采集 所以这里的最长应该是t3的
        System.out.println("采集信息完毕,最长花费时间：" + (System.currentTimeMillis() - begin));
    }

}

class CaptureRunnable implements Runnable {

    private String name;
    private long expiredTime;

    public CaptureRunnable(String name, long expiredTime) {
        this.name = name;
        this.expiredTime = expiredTime;
    }
    @Override
    public void run() {
        try {
            long beginTime = System.currentTimeMillis();
            Thread.sleep(expiredTime);
            System.out.println(Thread.currentThread().getName() + "采集信息花了" + (System.currentTimeMillis() - beginTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
