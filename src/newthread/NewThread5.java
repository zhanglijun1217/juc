package newthread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * created by zlj on 2018/5/31
 * 定时器去创建线程
 */
public class NewThread5 {

    public static void main(String[] args) {

        // 使用Timer类 去创建线程
        Timer timer = new Timer("timer thread");
        // 无延迟 每隔一秒执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 定时线程任务is running");
            }
        }, 0, 1000);
    }
}
