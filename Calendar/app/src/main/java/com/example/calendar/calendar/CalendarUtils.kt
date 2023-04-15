package com.example.calendar.calendar

import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object CalendarUtils {
    var currentDate: LocalDate = LocalDate.now()

    fun generateDaysInMonth(date: LocalDate): ArrayList<LocalDate?> {
        val daysInMonthArray: ArrayList<LocalDate?> = ArrayList()
        val yearMonth = YearMonth.from(date)

        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth: LocalDate = currentDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek)
                daysInMonthArray.add(null)
            else daysInMonthArray.add(
                LocalDate.of(currentDate.year, currentDate.month, i - dayOfWeek)
            )
        }
        return daysInMonthArray
    }

    fun monthYearFromDate(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
        return date.format(formatter)
    }

    fun dayMonthYearFromDate(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return date.format(formatter)
    }

    fun hoursMinutesFromTime(time: LocalTime): String {
        val hour = time.hour
        val minute = time.minute
        val am: Boolean

        val convertedHour: String = when {
            hour > 12 -> {
                am = false
                "${hour - 12}"
            }
            hour == 12 -> {
                am = false
                hour.toString()
            }
            else -> {
                am = true
                hour.toString()
            }
        }
        val convertedMinute: String = if (minute < 10) "0$minute" else minute.toString()
        val timeOfDay: String = if (am) "am" else "pm"
        return convertedHour.plus(":").plus(convertedMinute).plus(" ").plus(timeOfDay)
    }
}