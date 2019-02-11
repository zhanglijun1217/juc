package lesson.wwj.juc.thread_api.stacksize;

/**
 * Created by zlj on 2019/2/11.
 */
public class NewThreadWithStackSize {

    /**
     * 注意这里不是局部变量，所以这里的int i是放入方法区的
     */
    private int i = 0;

    /**
     * bytes这个变量存放在方法区中，里面存放的是对象在堆内存中的地址
     */
    private byte[] bytes = new byte[1024];

    private static int count = 0;

    public static void main(String[] args) {

        // main函数也是一个线程
        // create a thread by jvm named 'main' and 创建一个虚拟机栈

        // 局部变量 放入局部变量表中
        int j = 0;
        // arr在局部变量表中，对象还是放入堆内存中
        int[] arr = new int[1024];

        /**
         * 演示一个递归的操作 栈大小溢出
         */
        try {
            add(0);

        } catch (Error e) {
            // 抛出的是一个error异常 Stack Overflow
            e.printStackTrace();
            // 打印大概的压栈出栈次数
            System.out.println(count);
        }
    }

    private static void add(int i) {

        ++count;
        add(i + 1);
    }
}
