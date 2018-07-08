package collection;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 简单根据CopyOnWrite容器的思想去实现一个map 只实现了get put putAll方法 且一些临界异常条件没有去处理
 *
 * @author 夸克
 * @create 2018/7/8 15:58
 */
public class CopyOnWriteMap<K, V> extends AbstractMap<K, V> implements Cloneable {

    private volatile Map<K, V> internalMap;

    public CopyOnWriteMap() {
        internalMap = new HashMap<>();
    }

    /**
     * 此方法未实现
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V get(Object key) {
        // 读时不加锁
        return internalMap.get(key);
    }

    @Override
    public V put(K key, V value) {
        // 写时复制加锁
        synchronized (this) {
            Map<K, V> newMap = new HashMap<K, V>(internalMap);
            V val = newMap.put(key, value);
            internalMap = newMap;
            return val;
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> data) {
        synchronized (this) {
            Map<K, V> newMap = new HashMap<>(internalMap);
            newMap.putAll(data);
            internalMap = newMap;
        }
    }


    public static void main(String[] args) {
        CopyOnWriteMap<Integer, Integer> copyOnWriteMap = new CopyOnWriteMap();
        // 初始化数据
        Map<Integer, Integer> map = new HashMap();
        for (int i = 0; i < 10; i++) {
            map.put(i, i);
        }

        // 读五次线程
        for (int i = 0; i < 5; i++) {
            Thread read = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 方便写线程写入数据 如果不加sleep 是读不到数据的，因为是在新复制的容器中写。
                        // 测试copyOnWrite思想
                        Thread.sleep(500);
                    } catch (InterruptedException e) {

                    }
                    System.out.println(copyOnWriteMap.get(5));
                }
            });
            read.start();
        }

        // 写线程
        Thread write = new Thread(new Runnable() {

            @Override
            public void run() {
                map.forEach((k, v) -> {
                    copyOnWriteMap.put(k, v);
                });
            }
        });
        write.start();

    }

}
