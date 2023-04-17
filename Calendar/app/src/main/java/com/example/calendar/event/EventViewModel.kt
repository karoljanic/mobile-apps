package com.example.calendar.event

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: EventsRepository
    val readAllData: LiveData<List<Event>>

    init {
        val eventDao = EventsDatabase.getDatabase(application).eventDao()
        repository = EventsRepository(eventDao)
        readAllData = repository.readAllData
    }

    fun insertEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertEvent(event)
        }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEvent(event)
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateEvent(event)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllEvents()
        }
    }

    fun getEventsNumber(date: LocalDate): Int {
        val events: List<Event> = readAllData.value ?: return 0

        return events.filter { event -> event.localDate == date }.size
    }
}