package lesson.wwj.juc.runnable;

/**
 * 模拟银行的柜台 这里面去做号码的自增
 * Created by zlj on 2018/12/31.
 */
public class TicketWindowRunnable implements Runnable{

    private int index = 1;

    private static final int MAX = 50;

    @Override
    public void run() {

        while (index <= MAX) {

            // println方法是加锁的，所以index++ 在输出的时候没有index++的线程安全问题
            System.out.println(Thread.currentThread().getName() + "的号码是" + index++);

        }

    }
}
