package com.designpattern.observer_pattern.subject;

import com.designpattern.observer_pattern.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ClassMonitor implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String announcement; // Trạng thái thông báo

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void setAnnouncement(String message) {
        this.announcement = message;
        System.out.println("\n📢 [Lớp trưởng gửi thông báo]: " + message);
        notifyObservers();
    }

    @Override
    public String getState() {
        return announcement;
    }
}
