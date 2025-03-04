package com.librarymanagementsystem.state_pattern;

public class TeamLeaderState implements EmployeeState {
    @Override
    public void performDuties() {
        System.out.println("Patrolling and assigning tasks.");
        System.out.println("Evaluating employee performance.");
    }
}
