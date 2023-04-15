package com.example.calendar.event

import java.time.LocalDate
import java.time.LocalTime

data class Event(
    val id: Int,
    val name: String,
    val localDate: LocalDate,
    val localTime: LocalTime,
    var rating: Int
)