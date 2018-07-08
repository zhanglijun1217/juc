package newthread;

/**
 * created by zlj on 2018/5/31
 * 继承Thread类实现创建线程
 */
public class NewThread2 extends Thread {

    public NewThread2(String name) {
        super(name);
    }

    @Override
    public void run() {

        while (!interrupted()) {// 这里的循环是当不被中断的时候 才执行
            System.out.println(getName() + " 线程运行");
        }
    }

    public static void main(String[] args) {
        NewThread2 t1 = new NewThread2("first thread");
        NewThread2 t2 = new NewThread2("second thread");

        t1.setDaemon(true);// 后台（守护）线程会随着主线程结束也结束
        t2.setDaemon(true);

        t1.start();
        t2.start();

        // 中断不用stop()方法 已经过时
        t1.interrupt();
        t2.interrupt();
//
        try {
            // 让主线程sleep两秒
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }
}
