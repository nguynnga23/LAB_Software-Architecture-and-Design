package com.designpattern.state_pattern;

public class Employee {
    private String name;
    private EmployeeState currentState; // Chức vụ hiện tại

    public Employee(String name, EmployeeState initialState) {
        this.name = name;
        this.currentState = initialState;
    }

    public void setState(EmployeeState newState) {
        this.currentState = newState;
        System.out.println(name + " has been promoted or changed role.");
    }

    public void performDuties() {
        System.out.println(name + "'s duties:");
        currentState.performDuties();
        System.out.println();
    }
}
