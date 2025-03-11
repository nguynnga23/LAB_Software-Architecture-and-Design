package com.designpattern.observer_pattern.subject;

import com.designpattern.observer_pattern.observer.Observer;

public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
    String getState();
}
