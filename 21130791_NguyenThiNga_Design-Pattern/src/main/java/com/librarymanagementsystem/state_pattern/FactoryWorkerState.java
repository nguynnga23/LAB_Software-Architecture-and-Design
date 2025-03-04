package com.librarymanagementsystem.state_pattern;

public class FactoryWorkerState implements EmployeeState {
    @Override
    public void performDuties() {
        System.out.println("Following safety protocols.");
        System.out.println("Logging work hours.");
    }
}
