package lock;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Lock接口的lockInterruptibly方法测试
 * 对于在等待lock的线程，如果调用interrupt可以通过抛出InterruptEx来结束等待锁
 */
public class LockInterruptiblyTest {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException{
        LockInterruptiblyTest lockInterruptiblyTest = new LockInterruptiblyTest();
        MyTask task1 = new MyTask(lockInterruptiblyTest, "task1线程");
        MyTask task2 = new MyTask(lockInterruptiblyTest, "task2线程");
        task1.start();
        Thread.sleep(200); // 保证让task1线程先获取到锁
        task2.start();
        task1.interrupt(); // task1获取到锁在执行业务逻辑并不会响应interrupt中断抛出异常
        task2.interrupt();// task2在等待锁时抛出异常

    }

    public void biz() throws InterruptedException{
        lock.lockInterruptibly();
        try {
            for (int i = 0; i < 10 ; i++) {
                System.out.println(Thread.currentThread().getName() + "第" + i + "次执行业务逻辑");
//                TimeUnit.MILLISECONDS.sleep(2000); //注意本身线程休眠就是响应interrupt中断方法的 这里模拟不要用sleep 会影响观察
                LockSupport.parkNanos(1000 * 1000 * 200); // LockSupport不响应中断
            }
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "finally释放了锁");
        }
    }


    static class MyTask extends Thread {
        private LockInterruptiblyTest lockInterruptiblyTest;

        public MyTask(LockInterruptiblyTest test, String name) {
            setName(name);
            this.lockInterruptiblyTest = test;
        }

        @Override
        public void run() {
            try {
                lockInterruptiblyTest.biz();
            } catch (InterruptedException e) {
                System.out.println(Arrays.toString(e.getStackTrace()));
                System.out.println(Thread.currentThread().getName() + "等待锁时被interrupt中断");
            }

        }
    }
}
