package com.p2.calendarapp;

import com.opencsv.exceptions.CsvException;
import com.p2.calendarapp.event.*;
import com.p2.calendarapp.event.update.UpdateMeeting;
import com.p2.calendarapp.reader.EventCsvReader;
import com.p2.calendarapp.reader.RawCsvReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class CalendarAppApplication {

	public static void main(String[] args) throws IOException, CsvException {

//		List<AbstractEvent> list = new ArrayList<>(); // Schedule 클래스 생성으로 코드 변경2
		Schedule schedule = new Schedule();

		// csv파일로 대량 등록
		EventCsvReader csvReader = new EventCsvReader(new RawCsvReader());
		String meetingCsvPath = "/data/meeting.csv";

		List<Meeting> meetings = csvReader.readMeetings(meetingCsvPath);
		meetings.forEach(schedule::add);
		schedule.printAll();

		System.out.println("수정 후..");
		meetings.get(0).validateAndUpdate(
				new UpdateMeeting(
						"new title",
						ZonedDateTime.now(),
						ZonedDateTime.now().plusHours(3),
						null,
						"A",
						"new agenda"
				)
		);

		meetings.get(0).delete(true);
		System.out.println("삭제 후..");

//		System.out.println("삭제 후..재수정 시도 Test");
//		meetings.get(0).validateAndUpdate(
//				new UpdateMeeting(
//						"new title",
//						ZonedDateTime.now(),
//						ZonedDateTime.now().plusHours(3),
//						null,
//						"A",
//						"new agenda"
//				)
//		);
		schedule.printAll();

//		HashSet<String> participants = new HashSet<String>();
//		participants.add("danny.kim");
//
//		Meeting meeting1 = new Meeting(
//				1, "meeting1", ZonedDateTime.now(), ZonedDateTime.now().plusHours(1),
//				participants, "meetingRoom", "스터디"
//			);
//		schedule.add(meeting1);
////		schedule.printBy(EventType.MEETING);
//
//		Todo todo1 = new Todo(
//				2, "todo1",
//				ZonedDateTime.now(), ZonedDateTime.now().plusHours(2),
//				"할 일 적기"
//		);
//		schedule.add(todo1);
////		schedule.printBy(EventType.TO_DO);
//
//		Todo todo2 = new Todo(
//				3, "todo2",
//				ZonedDateTime.now().plusHours(3), ZonedDateTime.now().plusHours(2),
//				"할 일 적기"
//		);
//		schedule.add(todo2);
//
////		// :: 연사자는 클래스의 이름이나 객체 참조를 메서드 이름에서 분리
////		// 각 요소에 대해 Event의 print를 호출
////		list.forEach(Event::print);
//
////			list.stream()
////	//				.filter(each -> each instanceof Meeting) // 추상 클래스에 메서드 추가로 코드 변경1
////					.filter(each -> each.support(EventType.MEETING)) // Meeting 에 대한 결과 값만 출력하고 싶은 경우
////					.forEach(Event::print);
////  Schedule 클래스 생성으로 코드 이동3, printBy()
//
//		schedule.printAll();

		SpringApplication.run(CalendarAppApplication.class, args);
	}

}
