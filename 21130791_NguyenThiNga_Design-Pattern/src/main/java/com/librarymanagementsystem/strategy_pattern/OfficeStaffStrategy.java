package com.librarymanagementsystem.strategy_pattern;

public class OfficeStaffStrategy implements WorkStrategy {
    @Override
    public void executeDuties() {
        System.out.println("Scheduling meetings.");
        System.out.println("Entering data.");
    }
}

