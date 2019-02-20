package lesson.wwj.juc.thread_api.interrupt;

/**
 * @author 夸克
 * @date 2019/2/21 00:24
 *
 * 类方法：interrupt方法 可以中断一个线程sleep join wait(统称为blocked)
 * 类方法：isInterrupt方法 判断一个线程是否中断
 * 静态方法 interrupted方法 是Thread的静态方法 这个方法是为了解决比如用lambda创建线程时去拿到新创建的线程是的interrupted状态
 */
public class ThreadInterrupt {
    private static final Object MOINTOR = new Object();

    public static void main(String[] args) throws Exception{


        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    System.out.println("收到打断信号");
                    // 这里就是外边的interrupt方法打断了这里的sleep
                    e.printStackTrace();
                }
                System.out.println(">>" + Thread.currentThread().getName() + ".." + Thread.interrupted());
            }
        });
        thread.start();
        Thread.sleep(100);
        System.out.println(thread.isInterrupted());
        // 调用interrupt
        thread.interrupt();
        System.out.println(thread.isInterrupted());



        // 中断wait方法 以重写Thread类run方法来创建线程
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                synchronized (MOINTOR) {
                    try {
                        // thread1.interrupt()方法中断wait
                        MOINTOR.wait();
                    } catch (InterruptedException e) {
                        System.out.println("wait方法被中断，线程状态：" + this.isInterrupted());
                        e.printStackTrace();
                    }
                }
            }
        };
        thread1.start();
        thread1.interrupt();


        // 中断join
        Thread joinThread = new Thread(() -> {
            try {

                Thread.sleep(5000);
            } catch (Exception e) {
                System.out.println("join线程被中断了!");
                e.printStackTrace();
            }
        });
        joinThread.start();

        // 这里要起一个线程去中断 join线程 因为执行了join之后 main线程就得等join线程执行完毕
        Thread interruptThread = new Thread(()-> {
           joinThread.interrupt(); //
        });
        interruptThread.start();

        joinThread.join();
    }

}
