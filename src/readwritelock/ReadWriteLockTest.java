package readwritelock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试类
 * @author 夸克
 * @create 2018/7/11 18:10
 */
public class ReadWriteLockTest {

    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();


        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(200);
            }catch (Exception e) {

            }
            if (i %3 == 0) {
                // 写线程：
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        readWriteLockDemo.setNumber(999);
                    }
                }).start();
            } else {
                // 读线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        readWriteLockDemo.get();
                    }
                }).start();
            }

        }
    }
}

class ReadWriteLockDemo {

    private int number;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public void setNumber(int number) {
        // 获取写锁
        Lock lock = readWriteLock.writeLock();
        lock.lock();
        try {
            this.number = number;
            System.out.println(Thread.currentThread().getName() + "修改了number");
        } finally {
            lock.unlock();
        }
    }

    public void get() {
        Lock lock = readWriteLock.readLock();
        // 这里注意因为lock也可能报错 应该放在try的外边，防止没有加锁而去调用unlock方法导致异常
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取到了number：" + number);
        } finally {
            lock.unlock();
        }
    }
}
