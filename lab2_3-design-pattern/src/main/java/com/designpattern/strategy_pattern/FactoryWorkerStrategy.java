package com.designpattern.strategy_pattern;

public class FactoryWorkerStrategy implements WorkStrategy {
    @Override
    public void executeDuties() {
        System.out.println("Following safety protocols.");
        System.out.println("Logging work hours.");
    }
}

