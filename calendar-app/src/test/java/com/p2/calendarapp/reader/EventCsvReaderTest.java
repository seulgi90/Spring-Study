package com.p2.calendarapp.reader;

import com.opencsv.exceptions.CsvException;
import com.p2.calendarapp.event.Meeting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventCsvReaderTest {

    private final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    @Mock
    private RawCsvReader rawCsvReader;

    @InjectMocks
    private EventCsvReader sut;

    @Test
    public void reader() throws IOException, CsvException {
        // given

        String path = "";
        // @Mock, @InjectMocks 활용으로 코드 삭제
//        EventCsvReader sut = new EventCsvReader(rawCsvReader);

        List<String[]> mockData = new ArrayList<>();
        mockData.add(new String[8]); // 헤더 제외 떄문에 설정

        int mockSize = 5;
        for (int i = 0; i < mockSize; i++) {
            mockData.add(generateMock(i));
        }

        when(rawCsvReader.readAll(path)).thenReturn(mockData);

        // when
        List<Meeting> meetings = sut.readMeetings(path);

        // then
        assertEquals(mockSize, meetings.size());
        assertEquals("title0", meetings.get(0).getTitle());
    }

    private String[] generateMock(int id) {
        String[] mock = new String[8]; // 8개의 컬럼

        mock[0] = String.valueOf(id);
        mock[1] = "Meeting" + id;
        mock[2] = "title" + id;
        mock[3] = "A,B,C" + id;
        mock[4] = "A1" + id;
        mock[5] = "test" + id;
        mock[6] = of(ZonedDateTime.now().plusHours(id));
        mock[7] = of(ZonedDateTime.now().plusHours(id + 1));

        return mock;
    }

    private static String of(ZonedDateTime dateTime) {
        return  dateTime.format(DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS));
    }

}