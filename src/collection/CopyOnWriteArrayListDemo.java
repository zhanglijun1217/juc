package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 写入并复制 解决多个线程修改时并发修改异常
 * 写入时复制一份数组，效率比脚低
 * 适合并发迭代遍历查找等场景
 * @author 夸克
 * @create 2018/7/7 16:58
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();

        for (int i=0; i<10; i++) {
            new Thread(helloThread).start();
        }
    }
}

class HelloThread implements Runnable{

    private static List<String> list = new CopyOnWriteArrayList<>();
    // 这里如果不是CopyOnWriteArrayList会报异常


    // 初始化一些数据
    static {
        list.add("aa");
        list.add("bb");
        list.add("cc");
    }

    @Override
    public void run() {
        Iterator<String> iterator = list.iterator();
        // 迭代中添加元素
        while (iterator.hasNext()) {
            System.out.println(iterator.next());

            list.add("dd");
            try {
                Thread.sleep(200);

            } catch (InterruptedException e) {

            }
        }
    }
}
