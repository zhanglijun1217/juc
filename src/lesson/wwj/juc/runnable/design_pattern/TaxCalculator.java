package lesson.wwj.juc.runnable.design_pattern;

/**
 * 税率计算器来说明Runnable在Thread中的设计模式——策略模式
 *
 * Created by zlj on 2019/1/7.
 */
public class TaxCalculator {

    private double salary;

    private double bonus;

    /**
     * 计算的策略 以接口定义
     */
    private CalculatorStrategy strategy;

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    /**
     * 构造函数
     * @param salary
     * @param bonus
     */
    public TaxCalculator(double salary, double bonus) {
        this.salary = salary;
        this.bonus = bonus;
    }


    /**
     * 计算税率的方法
     * @return
     */
    public double calTax() {
        return this.strategy.calculate(getSalary(), getBonus());
    }

    /**
     * 为
     * @param strategy
     */
    public void setStrategy(CalculatorStrategy strategy) {
        this.strategy = strategy;
    }
}
