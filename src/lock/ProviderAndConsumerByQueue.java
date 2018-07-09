package lock;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * 根据阻塞队列实现生产者消费者模式
 * BlockingQueue内部已经实现了同步的队列，实现方式为await和signal
 * put方法 容量到达最大时，自动阻塞
 * take方法 容量为0时，自动阻塞
 *
 * 注意边界条件的判断
 * @author 夸克
 * @create 2018/7/9 20:14
 */
public class ProviderAndConsumerByQueue {

    public static void main(String[] args) {

        ShopByQueue shopByQueue = new ShopByQueue();

        ProviderByQueue p1 = new ProviderByQueue(shopByQueue);
        p1.setNum(2);

        ProviderByQueue p2 = new ProviderByQueue(shopByQueue);
        p1.setNum(3);

        ConsumerByQueue c1 = new ConsumerByQueue(shopByQueue);
        c1.setNum(4);

        ConsumerByQueue c2 = new ConsumerByQueue(shopByQueue);
        c2.setNum(1);

        new Thread(p1, "生产者线程1").start();
        new Thread(p2, "生产者线程2").start();
        new Thread(c1, "消费者线程1").start();
        new Thread(c2, "消费者线程2").start();

    }
}

class ShopByQueue {


    private BlockingDeque<Object> list = new LinkedBlockingDeque<>(10);

    public void get(int num) {

        if (list.size() == 10) {
            System.out.println("【库存量】:" + 10 + "/t暂时不能执行生产任务!");

        }

        try {
            for (int i=0;i<num;i++) {
                System.out.println("此时队列中元素个数为" + list.size());
                list.put(i);
                System.out.println(Thread.currentThread().getName() + " 正在往队列中放元素");
            }
        } catch (Exception e) {

        }
    }

    public void sale(int num) {
        // 如果仓库存储量不足
        if (list.size() == 0)
        {
            System.out.println("【库存量】:0/t暂时不能执行生产任务!");
        }
        try {
            for (int i = 0; i < num; i++) {
                System.out.println("此时队列中元素个数为" + list.size());
                list.take();
                System.out.println(Thread.currentThread().getName() + "正在往队列中取元素");
            }
        } catch (Exception e) {

        }
    }
}

class ProviderByQueue implements Runnable{
    private ShopByQueue shopByQueue;

    private int num;
    public ProviderByQueue(ShopByQueue shopByQueue) {
        this.shopByQueue = shopByQueue;
    }

    @Override
    public void run() {
        shopByQueue.get(num);
    }

    public ShopByQueue getShopByQueue() {
        return shopByQueue;
    }

    public void setShopByQueue(ShopByQueue shopByQueue) {
        this.shopByQueue = shopByQueue;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

class ConsumerByQueue implements Runnable {
    private ShopByQueue shopByQueue;

    public ConsumerByQueue(ShopByQueue shopByQueue) {
        this.shopByQueue = shopByQueue;
    }
    private int num;

    @Override
    public void run() {
        shopByQueue.sale(num);
    }

    public ShopByQueue getShopByQueue() {
        return shopByQueue;
    }

    public void setShopByQueue(ShopByQueue shopByQueue) {
        this.shopByQueue = shopByQueue;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
