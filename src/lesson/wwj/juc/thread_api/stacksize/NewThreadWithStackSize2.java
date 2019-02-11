package lesson.wwj.juc.thread_api.stacksize;

/**
 * Created by zlj on 2019/2/11.
 */
public class NewThreadWithStackSize2 {

    private static int count = 0;

    public static void main(String[] args) {

        /**
         * 指定stackSize的new Thread构造函数
         */
        Thread thread = new Thread(null, new Runnable() {
            @Override
            public void run() {
                try {
                    add(1);
                } catch (Error r) {
                    r.printStackTrace();
                    System.out.println(count);
                }
            }

            private void add(int i) {
                count ++;
                add(i+1);
            }
        }, "test-stackSize", 1<<23); // 1 右移23位 大概是8mb 给当前线程thread分配了8m的stackSize 进行递归栈溢出
        // 这里的疑问？虚拟机栈是每个线程都独有的一份吗？

        thread.start();
    }
}
