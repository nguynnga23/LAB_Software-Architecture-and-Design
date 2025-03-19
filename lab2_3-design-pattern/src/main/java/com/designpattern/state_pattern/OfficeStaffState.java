package com.designpattern.state_pattern;

public class OfficeStaffState implements EmployeeState {
    @Override
    public void performDuties() {
        System.out.println("Scheduling meetings.");
        System.out.println("Entering data.");
    }
}

