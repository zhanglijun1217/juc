package singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 双重加锁dcl下的单例模式
 * volatile + synchronized两个锁
 */
public class DCLSingleton {

    // volatile关键字来防止初始化单例对象时 指令重排序
    // 这里的指令指的是初始化对象的三步
        // 1. 申请内存空间 2. 初始化对象 3.赋值对象地址给引用
        // 第2、3步可能出现指令重排序 导致提前判断singleton不为null返回
    public static volatile DCLSingleton singleton;

    private DCLSingleton(){System.out.println("初始化了");}

    public static DCLSingleton getInstance() {
        // 避免直接加锁 性能差
        if (singleton == null) {
            // 防止并发创建多实例
            synchronized (DCLSingleton.class) {
                // 再次判断防止并发创建时的问题
                if (singleton == null) {
                    // 申请内存空间、初始化对象、赋值空间地址值赋值引用
                    singleton = new DCLSingleton();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> System.out.println("单例对象" + DCLSingleton.getInstance()));
        }
        executorService.shutdownNow();
    }

}
