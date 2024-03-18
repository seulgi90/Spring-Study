package com.p2.calendarapp.event;

import com.p2.calendarapp.event.update.AbstractAuditableEvent;
import com.p2.calendarapp.event.update.UpdateMeeting;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Set;

public class Meeting extends AbstractEvent {

    private Set<String> participants; // 참석자들은 중복되지 않기 때문에 set사용
    private String meetingRoom;
    private String agenda;

    public Meeting(int id, String title,
                   ZonedDateTime startAt, ZonedDateTime endAt,
                   Set<String> participants, String meetingRoom, String agenda) {
        super(id, title, startAt, endAt);
        this.participants = participants;
        this.meetingRoom = meetingRoom;
        this.agenda = agenda;
    }
    @Override
    public void print() {
        System.out.printf("[회의] %s : %s%n", getTitle(), agenda);
    }

    @Override
    public boolean support(EventType type) {
        return type == EventType.MEETING;
    }

    @Override
    protected void update(AbstractAuditableEvent update) {

        UpdateMeeting meetingUpdate = (UpdateMeeting) update;

        this.participants = meetingUpdate.getParticipants();
        this.meetingRoom = meetingUpdate.getMeetingRoom();
        this.agenda = meetingUpdate.getAgenda();

    }
}
