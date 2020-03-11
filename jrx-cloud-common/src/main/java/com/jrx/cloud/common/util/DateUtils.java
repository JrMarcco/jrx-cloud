package com.jrx.cloud.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author hongjc
 * @version 1.0  2020/3/2
 */
public class DateUtils {

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static Date localDateTime2Date(LocalDateTime localDateTime, ZoneId zoneId) {
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return localDateTime2Date(localDateTime, DEFAULT_ZONE_ID);
    }

    public static Date localDate2Date(LocalDate localDate, ZoneId zoneId) {
        return Date.from(localDate.atStartOfDay().atZone(zoneId).toInstant());
    }

    public static Date localDate2Date(LocalDate localDate) {
        return localDate2Date(localDate, DEFAULT_ZONE_ID);
    }

    public static LocalDate toLocalDate(Date date, ZoneId zoneId) {
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    public static LocalDate toLocalDate(Date date) {
        return toLocalDate(date, DEFAULT_ZONE_ID);
    }
}
