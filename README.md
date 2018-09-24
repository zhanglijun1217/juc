## juc项目简单说明
- 这是java.util.concurrent并发包中的学习小项目，对一些并发包下的常用类的测试和demo。

## 目前接入的知识（持续更新中）
### atomic package：
1. 简单实现CAS思想的DEMO 
2. Automic包中的原子类去实现线程安全的 i++ 操作

### collection package:
1. 应用"写入时复制"的copyOnWriteArrayList的Demo
2. 实现一个简单的cow的Map容器。

### communication package:
1. 循环打印ABC这道题的应用Condition实现的Demo

### countdownLatch package:
1. 使用闭锁countDownLatch实现统计多个线程计算的时间消耗

### lock package:
1. 使用lock去解决线程安全问题
2. 经典的消费者生产者问题，应用三种方式。（wait/notify、await/signal、阻塞队列） 
3. 线程八锁问题

### newthread package:
1. 创建线程的几种方式 主流的线程池、继承Thread、实现Runnable接口、实现Callable接口等

### volatiletest package:
1. 对volatile保证内存可见性做了Demo。


### readWriteLock package:
1. 读写锁 注意读/锁线程，锁/锁线程应该互斥。而读/读线程可以共享


### threadPool package:
1. 线程池简单应用
2. 线程调度（应用 scheduledThreedPool）

### threadLocal package:
1. ThreadLocal的一个工具类
2. [个人总结的ThreadLocal](https://zhanglijun1217.github.io/blog/2018/08/16/%E5%B9%B6%E5%8F%91%E7%BC%96%E7%A8%8B%E2%80%94%E2%80%94ThreadLocal%E6%80%BB%E7%BB%93/)

### semaphore package:
1. 利用Semaphore实现的一个有边界的容器。这里是利用了其中的许可来实现最大的边界，来源于《java并发编程实战》。


## 写的不好的地方希望指正