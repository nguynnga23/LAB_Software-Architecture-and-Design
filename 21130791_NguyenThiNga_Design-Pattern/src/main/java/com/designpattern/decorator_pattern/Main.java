package com.designpattern.decorator_pattern;

public class Main {
    public static void main(String[] args) {
        // Nhân viên bình thường
        Employee emp = new BaseEmployee("Alice");
        emp.performDuties();

        System.out.println("\n--- Alice promoted to Team Leader ---");
        emp = new TeamLeaderDecorator(emp);
        emp.performDuties();

        System.out.println("\n--- Bob is a Chief Accountant ---");
        Employee bob = new ChiefAccountantDecorator(new BaseEmployee("Bob"));
        bob.performDuties();
    }
}
