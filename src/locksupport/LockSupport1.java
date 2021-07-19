package locksupport;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * * LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。LockSupport 提供park()和unpark()方法实现阻塞线程和解除线程阻塞，LockSupport和每个使用它的线程都与一个许可(permit)关联。permit相当于1，0的开关，默认是0，调用一次unpark就加1变成1，调用一次park会消费permit, 也就是将1变成0，同时park立即返回。再次调用park会变成block（因为permit为0了，会阻塞在这里，直到permit变为1）, 这时调用unpark会把permit置为1。每个线程都有一个相关的permit, permit最多只有一个，重复调用unpark也不会积累。
 *  * park()和unpark()不会有 “Thread.suspend和Thread.resume所可能引发的死锁” 问题，由于许可的存在，调用 park 的线程和另一个试图将其 unpark 的线程之间的竞争将保持活性
 *
 *
 * 实现了类似于wait notify的功能 但park不需要获取对象锁 也就是不需要放在syn块或者方法中
 * 中断时（线程调用interrupt方法） park不会抛出InterruptException异常 所以要在park方法之后自行判断中断逻辑，然后做额外的处理
 *
 * 这里park的参数就是线程阻塞的一个对象 方便看线程堆栈快照时的block at ....
 *
 * park 和 unpark会对每个线程维持一个许可（permit），所以即使先调用unpark，再调用park，也不会出现死锁
 *
 */
public class LockSupport1 {

    public static final String BLOCKER = new String();

    public static class Task extends Thread{
        public Task(String name) {
            super(name);
        }
        @Override
        public void run() {
                System.out.println("当前线程:"  + Thread.currentThread().getName());
                // 阻塞线程
                LockSupport.park(BLOCKER);
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "被中断了");
                }
                System.out.println("继续执行");
        }
    }

    public static void main(String[] args) throws InterruptedException{
        Task task1 = new Task("task1");
        Task task2 = new Task("task2");

        task1.start();
        task2.start();
        sleep(3000);
//        task1.interrupt();
//        LockSupport.unpark(task2);
        new CountDownLatch(1).await();

    }

    static void sleep(long s){
        try {
            Thread.sleep(s);
        } catch (Exception  e){}
    }
}
