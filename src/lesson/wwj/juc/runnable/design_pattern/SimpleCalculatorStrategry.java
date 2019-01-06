package lesson.wwj.juc.runnable.design_pattern;

/**
 * 一个简单的策略模式的实现
 *
 * Created by zlj on 2019/1/7.
 */
public class SimpleCalculatorStrategry implements CalculatorStrategy{

    @Override
    public double calculate(double salary, double bonus) {
        return 0.1d * salary + 0.15 * bonus;
    }
}
