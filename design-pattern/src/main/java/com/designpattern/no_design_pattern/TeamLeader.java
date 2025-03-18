package com.designpattern.no_design_pattern;

public class TeamLeader extends Employee{
    public TeamLeader(String name) {
        super(name);
    }

    @Override
    public void performDuties() {
        evaluatePerformance();
    }

    public void evaluatePerformance() {
        System.out.println(name + " is evaluating employee performance.");
    }
}
