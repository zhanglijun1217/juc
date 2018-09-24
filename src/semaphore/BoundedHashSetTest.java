package semaphore;

/**
 * @author 夸克
 * @date 2018/9/24 22:24
 */
public class BoundedHashSetTest {

    public static void main(String[] args) throws InterruptedException{
        BoundedHashSet<Integer> set = new BoundedHashSet<>(10);
        try {
            for (int i=0; i<100; i++) {
                boolean add = set.add(i);
            }
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                System.out.println("容器已满" + e);
                System.out.println("容器内数据" + set.toString());
            } else if (e instanceof InterruptedException) {
                System.out.println("线程被中断");
            }
        }
    }
}
