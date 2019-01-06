package lesson.wwj.juc.runnable.design_pattern;

/**
 * 计算奖金策略接口  类比runnable方法
 *
 * Created by zlj on 2019/1/7.
 */
@FunctionalInterface
public interface CalculatorStrategy {

    double calculate(double salary, double bonus);
}
