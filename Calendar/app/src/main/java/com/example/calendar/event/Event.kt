package com.example.calendar.event

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Parcelize
data class Event(
    val id: Int,
    val name: String,
    val localDate: LocalDate,
    val localTime: LocalTime,
    var rating: Int
) : Parcelable
