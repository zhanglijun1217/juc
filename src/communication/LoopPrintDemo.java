package communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 循环打印ABC
 * @author 夸克
 * @create 2018/7/10 09:49
 */
public class LoopPrintDemo {

    public static void main(String[] args) {
        LoopPrint loopPrint = new LoopPrint();
        Thread t1  = new Thread(new Runnable() {
            @Override
            public void run() {
                loopPrint.printA();
            }
        }, "A");
        // 设置随主线程停止之后停止 这样可以让程序停止
        t1.setDaemon(true);



        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                loopPrint.printB();
            }
        }, "B");
        t2.setDaemon(true);

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                loopPrint.printC();
            }
        }, "C");
        t3.setDaemon(true);

        t1.start();
        t2.start();
        t3.start();

        try {
            // sleep方便观察
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }

}

class LoopPrint {
    private Lock lock = new ReentrantLock();

    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void printA() {
        try {
            lock.lock();
            // A打印5遍
            for (int i = 1 ;i <= 5; i++) {
                System.out.println("A");
            }
            condition1.await();
            // 条件变量2可以被唤醒
            condition2.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

    }

    public void printB() {
        try {
            lock.lock();
            // B打印2遍
            for (int i = 1 ;i <= 2; i++) {
                System.out.println("B");
            }
            condition2.await();
            // 条件变量3可以被唤醒
            condition3.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        try {
            lock.lock();
            // C打印2遍
            for (int i = 1 ;i <= 10; i++) {
                System.out.println("C");
            }
            condition3.await();
            // 条件变量1可以被唤醒
            condition1.signal();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
    }

}
