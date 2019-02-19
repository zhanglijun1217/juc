package lesson.wwj.juc.thread_api.deamon_thread;

/**
 * 这里对后台线程提出一个问题：
 * （1）当在main函数中的一个Thread里再创建一个线程，设置为后台线程，那么外边线程结束之后 里面的线程是否也会退出？ 会的 这个就长连接中的健康检查
 *
 *
 * @author 夸克
 * @date 2019/2/19 00:21
 */
public class DaemonQuestionThread {

    public static void main(String[] args) {
        Thread outerThread = new Thread(() -> {
            Thread innerThread = new Thread(() -> {
               try {
                   while (true) {
                       System.out.println("do Something for health check");
                       Thread.sleep(1_000);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
            });
            // 设置一个守护线程 设置的过程必须在start方法之前
            innerThread.setDaemon(true);
            innerThread.start();
        });

        try {
            Thread.sleep(1_000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        outerThread.start();
        System.out.println("程序结束");
    }
}
