package com.designpattern.observer_pattern;

import com.designpattern.observer_pattern.observer.Logger;
import com.designpattern.observer_pattern.observer.Mailer;
import com.designpattern.observer_pattern.observer.Protector;
import com.designpattern.observer_pattern.observer.Student;
import com.designpattern.observer_pattern.subject.ClassMonitor;

public class Main {
    public static void main(String[] args) {
        // Tạo lớp trưởng
        ClassMonitor monitor = new ClassMonitor();

        // Tạo danh sách observer
        Student student1 = new Student("Nga", monitor);
        Student student2 = new Student("Huy", monitor);
        Logger logger = new Logger(monitor);
        Mailer mailer = new Mailer(monitor);
        Protector protector = new Protector(monitor);

        // Đăng ký observer vào Subject
        monitor.addObserver(student1);
        monitor.addObserver(student2);
        monitor.addObserver(logger);
        monitor.addObserver(mailer);
        monitor.addObserver(protector);

        // Lớp trưởng gửi thông báo bình thường
        monitor.setAnnouncement("Ngày mai có buổi kiểm tra Toán.");

        // Thử thông báo có nội dung vi phạm
        System.out.println("\n--- Thử thông báo vi phạm ---");
        monitor.setAnnouncement("Thông báo có nội dung vi phạm!");
    }
}
