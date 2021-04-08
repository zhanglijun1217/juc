package threadLocal;

import java.util.concurrent.CountDownLatch;

/**
 * InheritableThreadLocal的测试
 * 子线程继承父线程上下文的 线程传递变量工具
 *
 * 利用线程中的inheritableThreadLocals变量
 */
public class InheritableThreadLocalTest {

    public static void main(String[] args) {

        InheritableThreadLocal<String> context = new InheritableThreadLocal<String>() {
            @Override
            protected String initialValue() {
                return "";
            }
        };

        context.set("线程名称:" + Thread.currentThread().getName());

        new Thread(() -> {
            // 这里运行已经是新的线程运行了
            System.out.println("子线程名称：" + Thread.currentThread().getName());
            // 从context中获取 此时已经是子线程了
            System.out.println("context中获取值：" + context.get());
        }).start();

        try {
            new CountDownLatch(1).await();
        } catch (Exception e) {

        }
    }
}
