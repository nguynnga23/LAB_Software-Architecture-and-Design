package com.librarymanagementsystem.decorator_pattern;

public class BaseEmployee implements Employee {
    private String name;

    public BaseEmployee(String name) {
        this.name = name;
    }

    @Override
    public void performDuties() {
        System.out.println(name + " performs basic employee duties.");
    }
}

