package com.librarymanagementsystem.no_design_pattern;

public class OfficeStaff extends Employee{
    public OfficeStaff(String name) {
        super(name);
    }

    public void scheduleMeetings() {
        System.out.println(name + " is scheduling meetings.");
    }

    public void dataEntry() {
        System.out.println(name + " is entering data.");
    }

    @Override
    public void performDuties() {
        scheduleMeetings();
        dataEntry();
    }
}
