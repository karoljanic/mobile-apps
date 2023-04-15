package com.example.calendar.calendar

import java.time.LocalDate

interface CalendarRecyclerViewInterface {
    fun onClickPosition(position: Int, localDate: LocalDate?)
}