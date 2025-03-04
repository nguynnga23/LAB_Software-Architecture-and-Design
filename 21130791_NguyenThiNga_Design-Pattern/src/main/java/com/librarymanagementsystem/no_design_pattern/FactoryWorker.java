package com.librarymanagementsystem.no_design_pattern;

public class FactoryWorker extends Employee{
    public FactoryWorker(String name) {
        super(name);
    }

    public void followSafetyProtocols() {
        System.out.println(name + " is following safety protocols.");
    }

    public void logWorkHours() {
        System.out.println(name + " is logging work hours.");
    }

    @Override
    public void performDuties() {
        followSafetyProtocols();
        logWorkHours();
    }
}
