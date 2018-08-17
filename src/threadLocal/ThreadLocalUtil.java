package threadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author 夸克
 * @create 2018/8/17 17:03
 */
public class ThreadLocalUtil {

    /**
     * 不同的业务区分ThreadLocal中map的key
     * （这里的map不是threadLocal中对应线程的threadLocalMap，而是要塞入线程中的map的值，
     * 这里可能在一个业务域中一个线程存在多次使用ThreadLocal，所以在threadLocal中塞入的是个map。而
     * 当前线程中存放的是<threadLocal对象,<业务key, 真正要使用的变量>>）
     * threadLocal内存泄漏问题（（1）ThreadLocalMap中Entry的引用没有释放）在jdk8中得到了解决，
     * 对ThreadLocalMap中的键值threadLocal实例的引用改为弱引用
     * 所以建议使用ThreadLocal
     */


    /**
     * 业务前缀key值的维护
     */
    public enum Key {

        /**
         * 测试使用
         */
        COMMON_TEST("COMMON_TEST");

        private String key;

        Key(String key) {
            this.key = key;
        }

    }

    private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * set方法
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        if(THREAD_LOCAL.get() == null) {
            // 初始化
            init();
        }
        if (key == null || key.isEmpty() || Objects.isNull(value)) {
            return;
        }
        THREAD_LOCAL.get().put(key, value);
    }

    /**
     * get方法
     * @param key
     * @return
     */
    public static Object get(String key) {
        if (key == null || key.isEmpty()) {
            return null;
        }
        return THREAD_LOCAL.get().get(key);
    }

    /**
     * 刷新方法
     */
    public static void refresh() {
        if (THREAD_LOCAL.get() == null) {
            return;
        }
        // map清除 key value
        THREAD_LOCAL.get().clear();
        // 清除map
        THREAD_LOCAL.set(null);
        // 线程中ThreadLocalMap remove
        THREAD_LOCAL.remove();
    }

    private static void init() {
        THREAD_LOCAL.set(new HashMap<>());
    }
}
