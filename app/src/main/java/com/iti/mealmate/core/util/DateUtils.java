package com.iti.mealmate.core.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public final class DateUtils {

    private DateUtils() {
    }

    public static long todayAtStartOfDay() {
        return LocalDate.now()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

    }

    public static long dateToTimeStamp(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();

    }

    public static LocalDate timestampToLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }



    public static LocalDate endOfNextWeek() {
        LocalDate today = LocalDate.now();
        int daysUntilNextSaturday = DayOfWeek.SATURDAY.getValue() - today.getDayOfWeek().getValue();
        if (daysUntilNextSaturday <= 0) {
            daysUntilNextSaturday += 7;
        }

        LocalDate nextSaturday = today.plusDays(daysUntilNextSaturday);

        return nextSaturday.plusDays(6);
    }

    public static long endOfNextWeekMillis() {

        return endOfNextWeek()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
