package newthread;

/**
 * created by zlj on 2018/5/31
 * 使用匿名内部类去创建线程
 */
public class NewThread3 {

    public static void main(String[] args) {

         //创建线程
        new Thread(){
            // 重写run方法
            public void run() {
                System.out.println("thread running...");
            }
        }.start();

        // 内部中实现runnable的run方法
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread running...");
            }
        }){

        }.start();


        /**
         * 匿名内部类中两种实现都有的时候 会优先执行子类的run方法
         */
        new Thread(new Runnable()
            // 实现runnable接口中的run方法
        {
            @Override
            public void run() {
                System.out.println("aaaaa");
            }

        })

            // 重写Thread父类的方法
        {
            public void run() {
                System.out.println("bbbbbb");
            }

        }.start();

    }
}
