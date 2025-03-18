package com.designpattern.observer_pattern.observer;

import com.designpattern.observer_pattern.subject.Subject;

public class Protector implements Observer {
    private Subject subject;

    public Protector(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void update() {
        String message = subject.getState();
        if (message.contains("vi phạm")) {
            System.out.println("⚠️ [Protector] Cảnh báo: Thông báo chứa nội dung vi phạm!");
        } else {
            System.out.println("✅ [Protector] Thông báo hợp lệ.");
        }
    }
}
