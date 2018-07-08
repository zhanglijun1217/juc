package atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一、i++的原子性 （不具有原子性 读/改/写）
 *          int i = 10;
 *          i = i++; / 结果是10
 *          因为 i++ 的操作分为三步  int temp = i; i = i + 1; i = temp;
 *
 * @author 夸克
 * @create 2018/7/7 16:01
 */
public class AtomicTest {

    public static void main(String[] args) {

        AtomicDemo atomicDemo = new AtomicDemo();

        for (int i=0; i<10; i++) {
            new Thread(atomicDemo).start();
        }
    }
}

class AtomicDemo implements Runnable {

    private AtomicInteger number = new AtomicInteger(0);

    @Override
    public void run() {
        // sleep方便观察
        try {
            Thread.sleep(200);
        } catch (Exception e) {

        }
        System.out.println(getNumber());

    }

    public int getNumber() {
        return number.getAndIncrement();
    }
}


