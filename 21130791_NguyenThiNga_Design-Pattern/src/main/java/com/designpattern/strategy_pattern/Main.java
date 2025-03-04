package com.designpattern.strategy_pattern;

public class Main {
    public static void main(String[] args) {
        // Tạo một nhân viên với chiến lược ban đầu là nhân viên văn phòng
        Employee emp = new Employee("Bob", new OfficeStaffStrategy());
        emp.performDuties();

        // Chuyển thành Đội trưởng
        emp.setWorkStrategy(new TeamLeaderStrategy());
        emp.performDuties();

        // Chuyển thành Giám đốc
        emp.setWorkStrategy(new FactoryWorkerStrategy());
        emp.performDuties();
    }
}


