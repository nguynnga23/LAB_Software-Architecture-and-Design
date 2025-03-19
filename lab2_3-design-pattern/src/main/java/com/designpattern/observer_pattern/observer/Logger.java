package com.designpattern.observer_pattern.observer;

import com.designpattern.observer_pattern.subject.Subject;

public class Logger implements Observer {
    private Subject subject;

    public Logger(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void update() {
        System.out.println("📜 [Logger] Ghi log thông báo: " + subject.getState());
    }
}
