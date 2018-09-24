package semaphore;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 使用Semaphore为容器设定边界 from 《java并发编程实战》
 *
 * 信号量的计数值会初始化为容器容量的最大值 add操作在添加元素之前，要获取Semaphore的一个许可。如果add操作
 * 没有添加任何一个元素，那么立刻释放许可。remove操作释放一个许可，使得可以更多的元素添加到容器中。
 *
 * @author 夸克
 * @date 2018/9/24 22:11
 */
public class BoundedHashSet<T> {

    private final Set<T> set;
    private final Semaphore semaphore;

    /**
     * 初始化容器和信号量
     *
     * @param bound 信号量总许可数量
     */
    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        this.semaphore = new Semaphore(bound);
    }

    /**
     * 添加操作
     * @param o
     * @return
     * @throws InterruptedException
     */
    public boolean add(T o) throws InterruptedException{
        if (semaphore.availablePermits() == 0) {
            throw new RuntimeException("超出信号量大小，容器已满");
        }
        // 获取一个许可
        semaphore.acquire();
        boolean isAdd = false;
        try {
            isAdd = set.add(o);
            return isAdd;
        } finally {
            // 如果没有添加成功 释放这个许可
            if (!isAdd) {
                semaphore.release();
            }
        }
    }

    /**
     * 删除操作
     * @param o
     * @return
     */
    public boolean remove(Object o) {
        boolean isRemove = set.remove(0);
        if (isRemove) {
            // 如果删除 释放一个许可
            semaphore.release();
        }
        return isRemove;
    }

    @Override
    public String toString() {
        return set.toString();
    }
}
