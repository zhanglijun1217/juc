package lesson.wwj.juc.thread_api.join;

import java.util.stream.IntStream;

/**
 * 哪个线程去调用join方法 就可以在加的线程内先执行该线程完毕之后才执行外部线程
 *
 * join方法可以加时间控制
 * join方法的底层实现其实是wait
 *
 * @author 夸克
 * @date 2019/2/20 00:11
 */
public class ThreadJoinTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            // t1线程
            IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "--" + i));
        });

        t1.start();
        Thread t2 = new Thread(() -> {
            // t2线程
            IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "--" + i));
        });

        t2.start();

        //
        try {
            // 调用了t1.join 会先输出t1 再输出t2
            t1.join();
            t2.join();// 这样写了之后 对于main线程来说 必须等到t1 和 t2线程执行完毕 才能执行main线程  但是对于t1 和 t2 是交替执行的
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("==================t1 和 t2执行完毕================");
        IntStream.range(1, 1000).forEach(i -> System.out.println(Thread.currentThread().getName() + "--" + i));

        // 如果这里调用 Thread.currentThread.join 则会出现程序无法关闭的问题。 main线程自己join了自己的情况
//        try {
//
//            Thread.currentThread().join();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
