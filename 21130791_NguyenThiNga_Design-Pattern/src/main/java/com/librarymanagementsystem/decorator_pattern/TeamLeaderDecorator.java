package com.librarymanagementsystem.decorator_pattern;

public class TeamLeaderDecorator extends EmployeeDecorator {
    public TeamLeaderDecorator(Employee employee) {
        super(employee);
    }

    @Override
    public void performDuties() {
        super.performDuties();
        assignTasks();
        patrolArea();
    }

    private void assignTasks() {
        System.out.println("Assigning tasks to employees.");
    }

    private void patrolArea() {
        System.out.println("Patrolling the work area.");
    }
}

