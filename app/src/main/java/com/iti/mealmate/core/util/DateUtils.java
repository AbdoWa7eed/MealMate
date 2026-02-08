package com.iti.mealmate.core.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

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


    public static boolean isCurrentWeek(LocalDate date) {
        LocalDate startOfWeek = LocalDate.now();
        while (startOfWeek.getDayOfWeek() != DayOfWeek.SATURDAY) {
            startOfWeek = startOfWeek.minusDays(1);
        }
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return !date.isBefore(startOfWeek) && !date.isAfter(endOfWeek);
    }

    public static String getFormattedDateRange(LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMM dd", Locale.getDefault());
        return startDate.format(dateFormat) + " - " + endDate.format(dateFormat);
    }

    public static String getTwoWeekDateRange() {
        LocalDate today = LocalDate.now();
        LocalDate startOfCurrentWeek = today;
        while (startOfCurrentWeek.getDayOfWeek() != DayOfWeek.SATURDAY) {
            startOfCurrentWeek = startOfCurrentWeek.minusDays(1);
        }
        LocalDate endOfNextWeek = startOfCurrentWeek.plusDays(13);

        return getFormattedDateRange(startOfCurrentWeek, endOfNextWeek);

    }
}
