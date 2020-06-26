package com.example.hotel.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qin
 * @date 2020-05-31
 */
public class DateTimeUtil {
    public static final String LATEST_CHECK_IN_TIME = "22:00:00";
    public static final int LATEST_DELAY_CHECK_IN = 2;
    public static final long TWO_HOURS_IN_SECOND= 2 * 60 * 60;
    /**
     *
     * @param date "yyyy-MM-dd"格式字符串
     * @param time "HH:mm:ss"格式字符串
     * @return dateTime LocalDateTime类型
     */
    public static LocalDateTime dateTimeStr2LocalDateTime(String date, String time){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(date+" "+time, dtf);
    }

    public static int compare(String date, String time, LocalDateTime current, int day, int hour){
        LocalDateTime dataTime = dateTimeStr2LocalDateTime(date, time);
        return dataTime.plusDays(day).plusHours(hour).compareTo(current);
    }

    public static int compare(LocalDateTime dateTime, LocalDateTime current, int day, int hour){
        return dateTime.plusDays(day).plusHours(hour).compareTo(current);
    }

}
