package com.designpattern.strategy_pattern;

public class Employee {
    private String name;
    private WorkStrategy workStrategy;

    public Employee(String name, WorkStrategy workStrategy) {
        this.name = name;
        this.workStrategy = workStrategy;
    }

    public void setWorkStrategy(WorkStrategy newStrategy) {
        this.workStrategy = newStrategy;
        System.out.println(name + " has changed work strategy.");
    }

    public void performDuties() {
        System.out.println(name + "'s duties:");
        workStrategy.executeDuties();
        System.out.println();
    }
}

