package lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock同步锁解决线程安全问题
 * @author 夸克
 * @create 2018/7/8 22:26
 */
public class LockTest {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        Thread thread1 = new Thread(ticket, "线程1");
        thread1.setPriority(10);
        Thread thread2 = new Thread(ticket, "线程2");
        thread2.setPriority(1);
        thread1.start();
        thread2.start();
    }
}

class Ticket implements Runnable{

    private int ticket = 100;

    private Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    @Override
    public void run() {

        // 这里只有一个线程能对票进行售卖
        lock.lock();// 上锁
        try {
            while (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "完成售票，余票为：" + --ticket);
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "解锁");
        }
    }
}

