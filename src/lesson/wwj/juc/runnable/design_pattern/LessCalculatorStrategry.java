package lesson.wwj.juc.runnable.design_pattern;

/**
 * 更低的税的一种计算方式
 *
 * Created by zlj on 2019/1/7.
 */
public class LessCalculatorStrategry implements CalculatorStrategy {

    @Override
    public double calculate(double salary, double bonus) {
        return 0.01d * salary + bonus *0.01d;
    }
}
