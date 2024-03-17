package com.p2.calendarapp.event;

import java.util.ArrayList;
import java.util.List;

public class Schedule {

    private List<AbstractEvent> events = new ArrayList<>();

    public void add(AbstractEvent event) {
        if(hasScheduleConflictWith(event)) {
            return;
        }
        this.events.add(event);
    }

    // 개별 출력
    public void printBy(EventType type) {
        events.stream()
                .filter(event -> event.support(type))
                .forEach(Event::print);
    }

    // 전체 출력
    public void printAll() {
        events.forEach(Event::print);
    }

    // 기존에 있던 이벤트와 새 이벤트의 시작시간이나 종료시간이 겹치면 오류
    private boolean hasScheduleConflictWith(AbstractEvent event) {
        return events.stream()
                .anyMatch(each ->
                        (event.getStartAt().isBefore(each.getEndAt()) && event.getStartAt().isAfter(each.getEndAt()))
                                || (event.getStartAt().isAfter(each.getEndAt())) && event.getStartAt().isBefore(each.getEndAt()));
    }
}
