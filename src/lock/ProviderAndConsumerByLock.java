package lock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于lock对象实现的生产者消费者模式 await方法和signal方法
 * await()：导致当前线程等待，直到其他线程调用该Condition大的signal或signalAll方法唤醒该线程
 * signal()：唤醒在这个Lock对象上的等待的单个线程
 * signalAll()：唤醒在这个Lock对象上等待的所有线程
 * @author 夸克
 * @create 2018/7/9 16:33
 */
public class ProviderAndConsumerByLock {

    public static void main(String[] args) {
        // 商店对象
        ShopByLock shop = new ShopByLock();

        ProviderByLock provider1 = new ProviderByLock(shop);
        provider1.setNum(10);
        ProviderByLock provider2 = new ProviderByLock(shop);
        provider2.setNum(10);
        ProviderByLock provider3 = new ProviderByLock(shop);
        provider3.setNum(10);
        ProviderByLock provider4 = new ProviderByLock(shop);
        provider4.setNum(10);
        ProviderByLock provider5 = new ProviderByLock(shop);
        provider5.setNum(10);



        ProviderByLock provider6 = new ProviderByLock(shop);
        provider6.setNum(30);


        ConsumerByLock consumer1 = new ConsumerByLock(shop);
        consumer1.setNum(50);

        ConsumerByLock consumer2 = new ConsumerByLock(shop);
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

class ShopByLock {
    private final int MAX_VALIE = 100;

    private LinkedList<Object> linkedList = new LinkedList<>();

    private final Lock lock = new ReentrantLock();

    /**
     * 两个基于锁的信号量
     */
    private Condition full = lock.newCondition();
    private Condition empty = lock.newCondition();

    // 进货
    public void get(int num) {
        try {
            // 上锁
            lock.lock();
            while (linkedList.size() + num > MAX_VALIE) {
                System.out.println("要生产的产品数量：" + num +"，大于库存最大容量：" + linkedList.size() +"，不能进行生产");

                // 阻塞 condition
                try {
                    full.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 生产条件满足 生产num个商品
            for (int i=0;i<num;i++) {
                linkedList.add(new Object());
            }

            System.out.println("已经生产完" + num + "个商品，现在库存量为：" + linkedList.size());

            // 完成生产线程
            empty.signalAll();

        } finally {
            // 放锁
            lock.unlock();
        }

    }

    // 销售
    public void sale(int num){
        try {
            // 上锁
            lock.lock();
            while (num > linkedList.size()) {
                System.out.println("要销售的产品数量：" + num + "，大于现有库存量：" + linkedList.size() + "，不能进行消费");

                // 阻塞 condition
                try {
                    empty.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 消费满足 消费num个商品
            for (int i=0;i<num;i++) {
                linkedList.remove();
            }

            System.out.println("已经销售完" + num +"个商品，现在库存量为：" + linkedList.size());

            // 完成消费线程
            full.signalAll();

        } finally {
            // 放锁
            lock.unlock();
        }
    }
}

/**
 * 生产者
 */
class ProviderByLock implements Runnable{

    private ShopByLock shop;

    private int num;

    public ProviderByLock(ShopByLock shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        shop.get(num);
    }

    public ShopByLock getShop() {
        return shop;
    }

    public void setShop(ShopByLock shop) {
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
class ConsumerByLock implements Runnable{

    private ShopByLock shop;

    private int num;

    public ConsumerByLock(ShopByLock shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        shop.sale(num);
    }

    public ShopByLock getShop() {
        return shop;
    }

    public void setShop(ShopByLock shop) {
        this.shop = shop;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}




