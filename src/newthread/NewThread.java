package newthread;

/**
 * created by zlj on 2018/5/31
 * Runnable接口 创建线程
 */
public class NewThread implements Runnable {

    @Override
    public synchronized void run() {
        while (true) {
            try {
                // Thread.sleep(1000);// 调用超时等待使得线程进入阻塞状态 到达时间后线程到达就绪状态
                wait();// 线程通讯必须在同步代码块中 否则会报错IllegalMonitorStateException
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("自定义线程执行...");
        }
    }

    public static void main(String[] args) {
        NewThread newThread = new NewThread();

        // 线程初始化
        Thread thread = new Thread(newThread);// 构造函数是runnable接口参数

        thread.start();// 调用start方法使得线程进入就绪状态

        while (true) {
            synchronized (newThread) {// 这里同步代码块中监视的是同步的对象 对应上边wait方法获取的是this对象
                System.out.println("主线程执行...");
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                newThread.notifyAll();// notify方法必须在同步监视器中 否则会报错

            }
        }

    }
}
