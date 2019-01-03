package com.nikken.sendnotifications.util;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@AllArgsConstructor
public class DateHelper {

    private static final String DATE_FORMAT = "HH:mm:ss";
    private static final String YYYY_MM_DD = "yyyy/MM/dd";
    private static final String YYYY_MM_DD_hh_mm_ss = "yyyy-MM-dd";

    private String timeZoneDescription;

    @SneakyThrows
    public Date findHourInterval() {

        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        TimeZone tzInAmerica = TimeZone.getTimeZone(timeZoneDescription);
        formatter.setTimeZone(tzInAmerica);

        String sDateInAmerica = formatter.format(new Date());
        return formatter.parse(sDateInAmerica);
    }

    public String dateToString(Date customDate) {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        return dateFormat.format(customDate);
    }

    public String dateToStringForTestProcess(Date customDate) {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_hh_mm_ss);
        return dateFormat.format(customDate);
    }
}
