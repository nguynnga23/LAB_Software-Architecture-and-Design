package com.designpattern.strategy_pattern;

public class ChiefAccountantStrategy implements WorkStrategy {
    @Override
    public void executeDuties() {
        System.out.println("Analyzing budgets.");
        System.out.println("Performing tax calculations.");
    }
}

