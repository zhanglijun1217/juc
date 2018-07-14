package lock;

/**
 * 线程8锁test
 * 1.两个普通同步方法 两个线程 标准打印 // one two
 * 2.新增Thread.sleep方法 打印 // one two
 * 3.新增普通方法 getThree() // three one two
 * 4.两个普通同步方法 两个number对象 // two one
 * 5.修改getOne为同步静态方法 // two one
 * 6.修改两个同步方法都为静态方法 // one two
 * 7.一个静态同步方法 一个非静态同步方法 两个number对象 // two one
 * 8.两个静态同步方法 两个number对象 // one two
 *
 * 线程八锁关键：
 * 非静态方法的锁默认为 this 静态方法的锁为对应的 Class
 * 某一个时刻内，只能有一个线程持有锁 无论几个方法
 * @author 夸克
 * @create 2018/7/13 14:31
 */
public class Thread8LockTest {

    public static void main(String[] args) {
        Number number = new Number();

        Number number2 = new Number();

        new Thread(new Runnable() {

            @Override
            public void run() {
                number.getOne();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                number2.getTwo();
            }
        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                number.getThree();
//            }
//        }).start();

    }

}

class Number {
    public static synchronized void getOne() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {

        }
        System.out.println("one");
    }

    public static synchronized void getTwo() {
        System.out.println("two");
    }

    public void getThree() {
        System.out.println("three");
    }
}
