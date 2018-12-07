package limitFlow;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 利用信号量去控制限流
 * Semaphore 可以控制某个资源可被同时访问的个数，通过acquire()获取一个许可，而release()释放一个许可
 * 信号量的本质是控制某个资源可被同时访问的个数，在一定程度上可以控制某资源的访问频率，但不能精确控制
 * 非分布式限流
 *
 * 相对于Atomic的优点：对于瞬间的高并发，未获取许可的请求会被放入阻塞队列中排队，而不是像计数器那样直接拒绝请求，从而达到流量削峰的目的
 *
 * @author 夸克
 * @date 2018/12/7 17:43
 */
public class SemaphoreLimiterDemo2 {

    public static void main(String[] args) {

        // 定义一个Semaphore 5个许可
        Semaphore semaphore = new Semaphore(5);

        for (int i = 0; i < 10; i++) {
            new SemaphoreLimiterRunnable(semaphore).start();
        }
    }

}
class SemaphoreLimiterRunnable extends Thread{
    private Semaphore semaphore;

    public SemaphoreLimiterRunnable(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            // 获取一个许可
            semaphore.acquire();

            long endTime = (long) Math.random() * 10;

            System.out.println("线程：" + Thread.currentThread().getName() + "要吃饭了");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("线程：" + Thread.currentThread().getName() + "已经吃完了");

            // 释放许可
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


