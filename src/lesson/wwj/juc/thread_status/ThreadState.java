package lesson.wwj.juc.thread_status;

/**
 * Created by zlj on 2019/2/23.
 */
public class ThreadState {

    public static void main(String[] args) {
        // TimeWaiting 状态的线程
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();
        // Waiting状态的线程
        new Thread(new Waiting(), "WaitThread").start();
        // 模拟同步锁 即未抢占到锁的线程为blocked状态的线程
        new Thread(new Blocked(), "BlockedThread-1").start();
        new Thread(new Blocked(), "BlockedThread-2").start();

    }

    /**
     * 不断的进行睡眠 模拟线程的超时等待状态
     */
    static class TimeWaiting implements Runnable {

        @Override
        public void run() {
            while (true) {
                SleepUtils.second(1000);
            }
        }
    }

    /**
     * 该线程在Waiting.class上进行等待 模拟线程的等待状态
     */
    static class Waiting implements Runnable {


        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        // 进行等待
                        Waiting.class.wait();

                    } catch (InterruptedException e) {

                    }
                }
            }
        }
    }

    /**
     * 模拟线程阻塞状态，一个线程获取了Blocked.class的锁 并且不会释放该锁
     */
    static class Blocked implements Runnable {

        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    SleepUtils.second(1000);
                }
            }
        }
    }
}
