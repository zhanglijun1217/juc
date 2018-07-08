package atomic;

/**
 * @author 夸克
 * @create 2018/7/7 16:26
 */
public class CompareAndSwapDemo {

    public static void main(String[] args) {

        final CompareAndSwap compareAndSwap = new CompareAndSwap();
        for(int i=0; i<10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i1 = compareAndSwap.get();
                    try {
                        Thread.sleep(200);

                    } catch (Exception e) {

                    }
                    System.out.println(compareAndSwap.compareAndSet(i1, (int)Math.random() * 101));
                }
            }).start();
        }
    }

}
class CompareAndSwap{
    private int value;


    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int exceptedValue, int update) {
        int oldValue = value;
        if (oldValue == exceptedValue) {
            this.value = update;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int exceptedValue, int update) {
        return exceptedValue == compareAndSwap(exceptedValue, update);
    }
}
