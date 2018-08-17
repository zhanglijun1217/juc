package newthread;

import java.util.Arrays;
import java.util.List;

/**
 * created by zlj on 2018/5/31
 * 初识lambda表达式的并行流的使用
 */
public class NewThread7 {

    public static void main(String[] args) {

        List<Integer> values = Arrays.asList(10,20,30,40);
        System.out.println(add(values));
    }

    public static int add(List<Integer> values) {
        values.parallelStream().forEach(System.out :: println);
        values.parallelStream().forEachOrdered(System.out::println);// 并行打印数组是乱序的，这个方法是有序的foreach迭代
        return values.parallelStream().mapToInt(i -> i*2).sum();
    }

}
