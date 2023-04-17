package com.example.calendar.event

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "EventsTable")
@TypeConverters(LocalDateConverter::class, LocalTimeConverter::class)
@Parcelize
data class Event(
    val name: String,
    val localDate: LocalDate,
    val localTime: LocalTime,
    var rating: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable

