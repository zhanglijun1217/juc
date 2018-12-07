package exception;

/**
 * @author 夸克
 * @date 2018/12/7 14:17
 */
public class Demo {

    public static void main(String[] args) {

        try {

            Class.forName("aaaaaa");

        } catch (ClassNotFoundException e) {
            System.out.println("发生了ClassNotFoundException");
        }
    }
}
