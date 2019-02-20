package lesson.wwj.juc.thread_api.interrupt;

/**
 * @author 夸克
 * @date 2019/2/21 00:24
 *
 * 类方法：interrupt方法 如果，线程的当前状态处于非阻塞状态，那么仅仅是线程的中断标志被修改为true而已；如果线程的当前状态处于阻塞状态，
 * 那么在将中断标志设置为true后 如果是wait、sleep以及join三个方法引起的阻塞，那么会将线程的中断标志重新设置为false，并抛出一个InterruptedException；
 * 因此，通过interrupt方法真正实现线程的中断原理是：开发人员根据中断标志的具体值，来决定如何退出线程。
 *
 * 类方法：isInterrupt方法 判断一个线程是否中断
 * 静态方法 interrupted方法 是Thread的静态方法 这个方法是为了解决比如用lambda创建线程时去拿到新创建的线程是的interrupted状态
        -- 这两个方法都调用了一个native方法 但是传入参数不太一样 静态方法会清除线程的中断标志位；而类方法仅仅是返回当前interrupt状态
 */
public class ThreadInterrupt {
    private static final Object MOINTOR = new Object();

    public static void main(String[] args) throws Exception{


        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);// 阻塞状态 清除中断标志位 抛出异常
                } catch (Exception e) {
                    System.out.println("收到打断信号");
                    // 这里就是外边的interrupt方法打断了这里的sleep
                    e.printStackTrace();
                }
                System.out.println(">>" + Thread.currentThread().getName() + ".." + Thread.interrupted());
            }
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println(thread.isInterrupted());
        // 调用interrupt
        thread.interrupt();
        System.out.println(thread.isInterrupted());



//
//        // 中断wait方法 以重写Thread类run方法来创建线程
//        Thread thread1 = new Thread(){
//            @Override
//            public void run() {
//                synchronized (MOINTOR) {
//                    try {
//                        // thread1.interrupt()方法中断wait
//                        MOINTOR.wait();
//                    } catch (InterruptedException e) {
//                        System.out.println("wait方法被中断，线程状态：" + this.isInterrupted());
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        thread1.start();
//        thread1.interrupt();
//
//
//        // 中断join
//        Thread joinThread = new Thread(() -> {
//            try {
//
//                Thread.sleep(5000);
//            } catch (Exception e) {
//                System.out.println("join线程被中断了!");
//                e.printStackTrace();
//            }
//        });
//        joinThread.start();
//
//        // 这里要起一个线程去中断 join线程 因为执行了join之后 main线程就得等join线程执行完毕
//        Thread interruptThread = new Thread(()-> {
//           joinThread.interrupt(); //
//        });
//        interruptThread.start();
//
//        joinThread.join();
    }

}
