package com.librarymanagementsystem.no_design_pattern;

public abstract class Employee {
    protected String name;

    public Employee(String name) {
        this.name = name;
    }
    // Common methods for all employees
    public void attendMeetings() {
        System.out.println(name + " is attending meetings.");
    }

    public void submitReports() {
        System.out.println(name + " is submitting reports.");
    }

    public abstract void performDuties(
    );
}
