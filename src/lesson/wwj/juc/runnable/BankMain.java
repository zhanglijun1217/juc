package lesson.wwj.juc.runnable;

/**
 * 银行 这里去模拟叫号码的操作
 * Created by zlj on 2018/12/31.
 */
public class BankMain {

    public static void main(String[] args) {

        // runnable 可执行逻辑抽离出来
        final TicketWindowRunnable ticketWindowRunnable = new TicketWindowRunnable();

        Thread thread1 = new Thread(ticketWindowRunnable);

        Thread thread2 = new Thread(ticketWindowRunnable);

        Thread thread3 = new Thread(ticketWindowRunnable);

        thread1.start();
        thread2.start();
        thread3.start();




    }
}
