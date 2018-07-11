package readwritelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试类
 * 每次运行结果可能不一致 取决于写线程和读线程哪个快.
 * @author 夸克
 * @create 2018/7/11 18:10
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();


        // 多个读线程
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                        readWriteLockDemo.get();
                    } catch (Exception e) {

                    }
                }
            }).start();
        }

        // 写线程：
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    readWriteLockDemo.setNumber(999);
                } catch (Exception e) {

                }
            }
        }).start();
    }
}

class ReadWriteLockDemo {

    private int number;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public void setNumber(int number) {
        // 获取写锁
        Lock lock = readWriteLock.writeLock();
        try {
            lock.lock();
            this.number = number;
            System.out.println(Thread.currentThread().getName() + "修改了number");
        } finally {
            lock.unlock();
        }
    }

    public void get() {
        Lock lock = readWriteLock.readLock();
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + "读取到了number：" + number);
        } finally {
            lock.unlock();
        }
    }
}
