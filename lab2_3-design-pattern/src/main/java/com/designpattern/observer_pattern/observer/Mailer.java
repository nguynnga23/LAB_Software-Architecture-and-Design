package com.designpattern.observer_pattern.observer;

import com.designpattern.observer_pattern.subject.Subject;

public class Mailer implements Observer {
    private Subject subject;

    public Mailer(Subject subject) {
        this.subject = subject;
    }

    @Override
    public void update() {
        System.out.println("📧 [Mailer] Gửi email thông báo: " + subject.getState());
    }
}
