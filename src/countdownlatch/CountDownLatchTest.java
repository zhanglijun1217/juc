package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁 countdownLatch 某个线程做操作时，只有其他线程做完操作时，才能执行当前操作
 * @author 夸克
 * @create 2018/7/8 21:10
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        // 传入Runnable对象一个countdownLatch对象
        CountDownLatch countDownLatch = new CountDownLatch(5);
        CountdownLatchDemo countdownLatchDemo =new CountdownLatchDemo(countDownLatch);

        long start = System.currentTimeMillis();
        for (int i = 0; i<5;i ++) {
            new Thread(countdownLatchDemo).start();
        }

        try {
            // 唤醒主线程
            countDownLatch.await();
        } catch (Exception e) {

        }

        long end = System.currentTimeMillis();
        // 主线程去计算 这里逻辑应该是主线程在计算线程（latch减到0之后去计算运行时间）
        System.out.println("运行时间为" + (end - start));

    }

}
class CountdownLatchDemo implements Runnable{
    private CountDownLatch latch;

    public CountdownLatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        // 要使得countdownlatch闭锁减1 加锁防止线程安全问题
        synchronized (this) {
            try {
                // 计算1000以内的偶数
                for (int i=0;i<10000;i++) {
                    if (i % 2 == 0) {
                        System.out.println(i + " 当前栅栏数" + latch.getCount());
                    }
                }

            } finally {
                // 保证latch-1 放入finally
                latch.countDown();
            }
        }
    }
}