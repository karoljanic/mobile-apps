package com.example.calendar.event

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.toList
import java.time.LocalDate
import java.time.LocalTime

class EventsRepository(private val eventDao: EventDao) {
    val readAllData: LiveData<List<Event>> = eventDao.getAll()

    suspend fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    suspend fun updateEvent(event: Event) {
        eventDao.updateEvent(event)
    }

    suspend fun deleteEvent(event: Event) {
        eventDao.deleteEvent(event)
    }

    suspend fun deleteAllEvents() {
        eventDao.deleteAll()
    }

    fun getEvents(): List<Event>? {
        return readAllData.value
    }
}