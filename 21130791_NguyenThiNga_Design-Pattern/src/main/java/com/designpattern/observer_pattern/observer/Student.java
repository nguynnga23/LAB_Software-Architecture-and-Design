package com.designpattern.observer_pattern.observer;

import com.designpattern.observer_pattern.observer.Observer;
import com.designpattern.observer_pattern.subject.Subject;

public class Student implements Observer {
    private String name;
    private Subject subject;

    public Student(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }

    @Override
    public void update() {
        System.out.println("👩‍🎓 " + name + " nhận thông báo: " + subject.getState());
    }
}
