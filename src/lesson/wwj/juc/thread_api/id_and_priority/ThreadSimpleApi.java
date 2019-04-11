package lesson.wwj.juc.thread_api.id_and_priority;

import java.util.Optional;

/**
 * @author 夸克
 * @date 2019/2/19 00:32
 */
public class ThreadSimpleApi {

    public static void main(String[] args) {
        Thread t = new Thread(()-> {
            Optional.of("Hello").ifPresent(System.out::println);
            try {
                Thread.sleep(100000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"t1");


        t.start();
        // 简单api实用
        Optional.of(t.getName()).ifPresent(System.out::println);
        // id是怎么来的？ thread中有个tid字段做的 是一个synchronized中一个自增方法获取的 是几 则说明前面创建了几个线程
        Optional.of(t.getId()).ifPresent(System.out::println);

        // 优先级 只是一个概率 优先级高的不一定最先执行
        Optional.of(t.getPriority()).ifPresent(System.out::println);

    }
}
