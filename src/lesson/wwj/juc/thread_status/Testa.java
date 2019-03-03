package lesson.wwj.juc.thread_status;

import java.util.concurrent.atomic.AtomicInteger;

public class Testa {

    /**
     * 测试一个mian线程启动的时候有多少线程
     *
     * 当自定义线程执行完毕之后，有多少活跃线程(这里不要忘记了ctx线程)
     */

    public volatile AtomicInteger inc = new AtomicInteger();

    public void increase() {
        inc.incrementAndGet();
    }

    public static void main(String[] args) {
        final Testa test = new Testa();
        for(int i=0;i<10;i++){
            new Thread(){
                public void run() {
                    for(int j=0;j<1000;j++)
                        test.increase();
                };
            }.start();
        }
        // 还有其他线程 这里要大于2
        while(Thread.activeCount()>2) {
            Thread.yield();
            Thread.currentThread().getThreadGroup().list();
            System.out.println(Thread.activeCount());
        }

        System.out.println(test.inc);
    }

}