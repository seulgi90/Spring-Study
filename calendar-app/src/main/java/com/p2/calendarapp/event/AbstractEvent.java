package com.p2.calendarapp.event;

import com.p2.calendarapp.exception.InvalidEventException;

import java.time.Duration;
import java.time.ZonedDateTime;

// 공통으로 사용되는 컬럼의 추상클래스에 적의
public abstract class AbstractEvent implements Event {

    private final int id;
    private String title;
    private ZonedDateTime startAt;
    private ZonedDateTime endAt;
    private Duration duration;

    private final ZonedDateTime createAt;
    private ZonedDateTime modifiedAt;
    private boolean deleteYnl;

    protected AbstractEvent(int id, String title, ZonedDateTime startAt, ZonedDateTime endAt) {

        if (startAt.isAfter(endAt)) {
            throw new InvalidEventException(
                    String.format("시작일은 종료일보다 이전이어야 합니다. 시작일=%s, 종료일=%s", startAt, endAt)
            );
        }

        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.duration =  Duration.between(startAt, endAt); // 시작시간과 종료시간이 들어올 때 바로 계산되게 설정

        // ------- 내부에서 전달 받는 값들, 직접 할당

        // createAt과 modifiedAt은 객체가 만들어 질 당시에는 같은 값
        ZonedDateTime now = ZonedDateTime.now();
        this.createAt = now;
        this.modifiedAt = now;

        // 객체가 만들어 질 당시의 설정 값은 삭제가 안된 상태이기 때문
        this.deleteYnl = false;
    }

    public String getTitle() {
        return this.title;
    }

    public ZonedDateTime getStartAt() {
        return startAt;
    }

    public ZonedDateTime getEndAt() {
        return endAt;
    }
}
