package lesson.wwj.juc.runnable.design_pattern;

/**
 * Created by zlj on 2019/1/7.
 */
public class TaxCalculatorMain {

    public static void main(String[] args) {

        TaxCalculator taxCalculator = new TaxCalculator(10000, 2000);

        // 设置一种简答计算税率策略
        taxCalculator.setStrategy(new SimpleCalculatorStrategry());

        double v = taxCalculator.calTax();

        System.out.println("简答策略计算税率为" + v);

        // 换一种策略
        taxCalculator.setStrategy(new LessCalculatorStrategry());



        System.out.println("更少策略计算税率为" + taxCalculator.calTax());

        // 使用java8 functionInterface
        taxCalculator.setStrategy((s, b) -> s * 0.2 + b * 0.5);
        System.out.println("更多策略计算税率为" + taxCalculator.calTax());
    }
}
