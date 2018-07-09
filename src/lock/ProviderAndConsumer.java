package lock;

import java.util.LinkedList;
import sun.security.provider.SHA;

/**
 * 生产者和消费者模式 synchronized + wait/notify 实现
 * wait方法因为使用同步锁的对象头实现 必须在锁代码块中调用
 * 使用wait方法 这里要在while方法中使用
 *    防止虚假唤醒 这里如果只是简单用if判断，当多个消费者线程去消费时，一个消费者线程wait之后另一个消费者线程如果抢占到锁之后，没有判断
 *    库存量，这里会直接把库存-1 会产生很多问题
 *
 * @author 夸克
 * @create 2018/7/8 22:35
 */
public class ProviderAndConsumer {

    public static void  main(String[] args) {

        // 商店对象
        Shop shop = new Shop();

        Provider provider1 = new Provider(shop);
        provider1.setNum(10);
        Provider provider2 = new Provider(shop);
        provider2.setNum(10);
        Provider provider3 = new Provider(shop);
        provider3.setNum(10);
        Provider provider4 = new Provider(shop);
        provider4.setNum(10);
        Provider provider5 = new Provider(shop);
        provider5.setNum(10);



        Provider provider6 = new Provider(shop);
        provider6.setNum(30);


        Consumer consumer1 = new Consumer(shop);
        consumer1.setNum(50);

        Consumer consumer2 = new Consumer(shop);
        consumer2.setNum(30);

        new Thread(consumer1, "消费者1").start();
        new Thread(consumer2, "消费者2").start();


        new Thread(provider1, "生产者1").start();
        new Thread(provider2, "生产者2").start();
        new Thread(provider3, "生产者3").start();
        new Thread(provider4, "生产者4").start();
        new Thread(provider5, "生产者5").start();
        new Thread(provider6, "生产者6").start();

    }
}

/**
 * 商店类
 */
class Shop {

    private final int MAX_SIZE = 100;

    // 商品载体
    private LinkedList<Object> list = new LinkedList<>();

    // 进货
    public  void get(int num) {
        // 对list上锁
        synchronized(list) {
            // 库存量剩余不足
            while (num + list.size() > MAX_SIZE) { // 一定要在while循环中调用wait防范多个线程被 虚假唤醒
                System.out.println("要生产的产品数量：" + num +"，大于库存最大容量：" + list.size() +"，不能进行生产");

                // 生产阻塞
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 生产条件满足 生产num个商品
            for (int i=0;i<num;i++) {
                list.add(new Object());
            }

            System.out.println("已经生产完" + num + "个商品，现在库存量为：" + list.size());
            // 完成生产线程 释放锁
            list.notifyAll();
        }
    }

    // 卖货
    public void sale(int num) {
        synchronized (list) {
            // 如果库存量不足
            while (list.size() < num) {
                System.out.println("要销售的产品数量：" + num + "，大于现有库存量：" + list.size() + "，不能进行消费");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            // 满足消费情况 进行消费
            for (int i=0;i<num;i++) {
                list.remove();
            }

            System.out.println("已经消费完" + num +"个商品，现在库存量为：" + list.size());

            // 消费线程完成 唤醒其他线程
            list.notifyAll();
        }
    }

}

/**
 * 生产者
 */
class Provider implements Runnable{

    private Shop shop;

    private int num;

    public Provider(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        shop.get(num);
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

/**
 * 消费者
 */
class Consumer implements Runnable{

    private Shop shop;

    private int num;

    public Consumer(Shop shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        shop.sale(num);
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

