package com.iti.mealmate.ui.common;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import com.iti.mealmate.R;
import com.iti.mealmate.core.util.DateUtils;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.function.Consumer;

public class UiUtils {

    private UiUtils() {}
    public static int  dpToPx(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


    public static void showDatePicker(
            Context context,
            @NotNull Consumer<LocalDate> pickedDate
    ) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                context,
                R.style.CustomDatePickerDialogStyle,
                (view, year, month, dayOfMonth) -> {
                    pickedDate.accept(LocalDate.of(year, month + 1, dayOfMonth));
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        DatePicker datePicker = datePickerDialog.getDatePicker();
        datePicker.setMinDate(calendar.getTimeInMillis());
        datePicker.setMaxDate(DateUtils.endOfNextWeekMillis());

        datePickerDialog.show();
    }
}
