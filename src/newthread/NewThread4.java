package newthread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * created by zlj on 2018/5/31
 * 带有返回值 和 抛出异常 的线程创建
 */
public class NewThread4 implements Callable<Integer> {

    // 实现call方法
    // callable接口 和 runnable接口 比较：（1）callable接口可以传入泛型有返回值  （2）可以抛出异常
    @Override
    public Integer call() throws Exception {
        System.out.println("正在紧张的计算");
        // sleep 模拟计算过程
        Thread.sleep(3000);
        return 1;
    }

    public static void main(String[] args) {

        NewThread4 t = new NewThread4();
        /*
         * 对线程任务的封装
         */

        // 主线程可以先去做点别的
        System.out.println("先去做点别的");

        // 首先用FutureTask<T> 对 call 封装成任务
        FutureTask<Integer> task = new FutureTask<Integer>(t);

        // 再根据构造函数去封装成Thread对象 传入FutureTask类型的task
        Thread thread = new Thread(task);

        thread.start();

        // 拿到线程的结果
        try {
            System.out.println("计算的结果是" + task.get()); // 这里注意TaskFuture的get()方法是等上边线程结束之后去进行计算结果的。也是一个闭锁操作
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
