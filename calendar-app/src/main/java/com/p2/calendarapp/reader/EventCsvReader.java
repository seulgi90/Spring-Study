package com.p2.calendarapp.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.p2.calendarapp.event.Meeting;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class EventCsvReader {

    public List<Meeting> readMeetings(String path) throws IOException, CsvException {
        List<Meeting> result = new ArrayList<>();

        // 데이터 읽기
        List<String[]> read = readAll(path);
        for (int i = 0; i < read.size(); i++) {
            // 헤더 제외
            if (skipHeader(i)) {
                continue;
            }

            // Meeting 자바 객체로 변환
            String[] each = read.get(i);
            result.add(
                    new Meeting(
                            Integer.parseInt(each[0]),
                            each[2],
                            ZonedDateTime.of(
                                    LocalDateTime.parse(each[6], DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")),
                                    ZoneId.of("Asia/Seoul")),
                            ZonedDateTime.of(
                                    LocalDateTime.parse(each[7], DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss")),
                                    ZoneId.of("Asia/Seoul")),
                            new HashSet<>(Arrays.asList(each[3].split(","))),
                            each[4],
                            each[5]
                    )
            );
        }
        return result;
    }

    private static boolean skipHeader(int i) {
        return i == 0;
    }

    private List<String[]> readAll(String path) throws IOException, CsvException {
        InputStream in = getClass().getResourceAsStream(path);
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8); // csv파일로 변환

        CSVReader csvReader = new CSVReader(reader);
        return csvReader.readAll();
    }
}
