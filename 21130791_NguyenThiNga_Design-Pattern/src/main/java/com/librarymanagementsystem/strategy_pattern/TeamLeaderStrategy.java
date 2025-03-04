package com.librarymanagementsystem.strategy_pattern;

public class TeamLeaderStrategy implements WorkStrategy {
    @Override
    public void executeDuties() {
        System.out.println("Patrolling and assigning tasks.");
        System.out.println("Evaluating employee performance.");
    }
}

