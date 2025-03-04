package com.librarymanagementsystem.state_pattern;

public class Main {
    public static void main(String[] args) {
        // Tạo một nhân viên với chức vụ ban đầu là nhân viên văn phòng
        Employee emp = new Employee("Alice", new OfficeStaffState());
        emp.performDuties();

        // Thăng chức thành Đội trưởng
        emp.setState(new TeamLeaderState());
        emp.performDuties();

        // Lên chức Giám đốc
        emp.setState(new FactoryWorkerState());
        emp.performDuties();
    }
}

