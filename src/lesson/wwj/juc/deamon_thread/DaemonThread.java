package lesson.wwj.juc.deamon_thread;

/**
 * @author 夸克
 * @date 2019/2/18 23:57
 */
public class DaemonThread {

    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    // 默认为非后台线程
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("新起的线程开始");
                System.out.println("新起的线程结束");
            }
        };

        try {
//            Thread.sleep(50_000); // JDK 1.7的特性 这种写法
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(thread.getName());
        // 是否为守护线程 返回false
        System.out.println(thread.isDaemon());
        // 设置为守护线程 就会随着main线程的消亡而消亡
        thread.setDaemon(true);
        thread.start();
    }
    /**
     * 后台线程的一个用处 ：
     *      在设计长连接的时候，都会做一个心跳检查的检查，这时候就需要将这个线程设置为一个后台线程
     *      这样可以保证当整个应用不能用的时候，这个线程可以跟随主线程关闭掉
     */
}
