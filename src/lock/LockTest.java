package lock;

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
        for (int i=0 ; i<3;i ++) {
            new Thread(ticket).start();
        }
    }
}

class Ticket implements Runnable{

    private int ticket = 100;

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {

        lock.lock();// 上锁
        try {
            while (ticket > 0) {
                System.out.println(Thread.currentThread().getName() + "完成售票，余票为：" + --ticket);
                Thread.sleep(200);
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }
}

