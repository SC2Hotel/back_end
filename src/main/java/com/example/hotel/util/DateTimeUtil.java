package com.example.hotel.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qin
 * @date 2020-05-31
 */
public class DateTimeUtil {
    public final static String LATEST_CHECK_IN_TIME = "22:00:00";
    /**
     *
     * @param date "yyyy-MM-dd"格式字符串
     * @param time "HH:mm:ss"格式字符串
     * @return dateTime LocalDateTime类型
     */
    public static LocalDateTime dateTimeStr2LocalDateTime(String date, String time){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date+" "+time, dtf);
        return dateTime;
    }
}
