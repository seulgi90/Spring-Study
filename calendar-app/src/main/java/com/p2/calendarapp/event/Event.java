package com.p2.calendarapp.event;

public interface Event {
    void print();

    boolean support(EventType type);
}
