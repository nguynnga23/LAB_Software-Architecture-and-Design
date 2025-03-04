package com.librarymanagementsystem.state_pattern;

public class ChiefAccountantState implements EmployeeState {
    @Override
    public void performDuties() {
        System.out.println("Analyzing budgets.");
        System.out.println("Performing tax calculations.");
    }
}

