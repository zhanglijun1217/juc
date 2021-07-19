package singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 利用静态类初始化来实现安全单例
 * 利用classloder的机制来保证初始化instance时只有一个线程。JVM在类初始化阶段会获取一个锁，这个锁可以同步多个线程对同一个类的初始化。
 * 这里没有加volatile，因为即使初始化实例对象的指令发生了重排序，也只是在一个线程中 类加载时会上锁。
 */
public class StaticSingleton {

    private StaticSingleton() {System.out.println("类初始了");}

    private static class SingletonHolder {
        public static StaticSingleton singleton = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.singleton;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println("获取单例对象" + StaticSingleton.getInstance()));
        }
        executorService.shutdownNow();
    }
}
