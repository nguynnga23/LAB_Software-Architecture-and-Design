package com.librarymanagementsystem.decorator_pattern;

public abstract class EmployeeDecorator implements Employee {
    protected Employee decoratedEmployee;

    public EmployeeDecorator(Employee employee) {
        this.decoratedEmployee = employee;
    }

    @Override
    public void performDuties() {
        decoratedEmployee.performDuties();
    }
}
