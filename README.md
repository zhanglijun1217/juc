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

### newthread package:
1. 创建线程的几种方式 主流的线程池、继承Thread、实现Runnable接口、实现Callable接口等

### volatiletest package:
1. 对volatile保证内存可见性做了Demo。