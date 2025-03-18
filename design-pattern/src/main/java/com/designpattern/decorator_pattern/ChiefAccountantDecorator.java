package com.designpattern.decorator_pattern;

public class ChiefAccountantDecorator extends EmployeeDecorator {
    public ChiefAccountantDecorator(Employee employee) {
        super(employee);
    }

    @Override
    public void performDuties() {
        super.performDuties();
        analyzeBudgets();
        calculateTaxes();
    }

    private void analyzeBudgets() {
        System.out.println("Analyzing company budgets.");
    }

    private void calculateTaxes() {
        System.out.println("Performing tax calculations.");
    }
}
