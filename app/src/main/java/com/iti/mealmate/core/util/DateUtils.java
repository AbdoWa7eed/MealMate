package com.iti.mealmate.core.util;

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

    public static LocalDate timestampToLocalDate(long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
