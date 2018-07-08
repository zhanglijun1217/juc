package volatiletest;

/**
 * volatile关键字 当多个线程进行共享数据时，可以保证内存中数据可见性
 *
 * @author 夸克
 * @create 2018/7/8 16:40
 */
public class VolatileTest {

    public static void main(String[] args) {

        Demo demo = new Demo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {

                }
                while (!demo.isFlag()) {
                    System.out.println("flag是" + demo.isFlag());
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                demo.setFlag(true);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {

                }
            }
        }).start();

    }


}

class Demo {
    private volatile boolean flag; // 不用volatile的话 可能出现多线程访问时数据可见性问题

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}




