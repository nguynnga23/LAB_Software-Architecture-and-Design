package com.designpattern.no_design_pattern;

public class ChiefAccountant extends Employee{
    public ChiefAccountant(String name) {
        super(name);
    }

    public void analyzeBudgets() {
        System.out.println(name + " is analyzing budgets.");
    }

    public void taxCalculations() {
        System.out.println(name + " is performing tax calculations.");
    }

    @Override
    public void performDuties() {
        analyzeBudgets();
        taxCalculations();
    }
}
