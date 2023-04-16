package com.example.calendar.event

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalTime

object EventsRepository : Serializable {
    private lateinit var events: ArrayList<Event>
    private var idCounter: Int = 0

    fun initialize() {
        events = ArrayList()
        idCounter = 0
    }

    fun initialize(eventsList: ArrayList<Event>) {
        events = eventsList
        idCounter = eventsList.size
    }

    fun add(name: String, localDate: LocalDate, localTime: LocalTime) {
        events.add(Event(idCounter, name, localDate, localTime, 0))
        idCounter++
    }

    fun updateRating(event: Event, newRating: Int) {
        events[events.indexOf(event)].rating = newRating
    }

    fun getAll(): ArrayList<Event> {
        return events
    }


    fun get(localDate: LocalDate): List<Event> {
        return events.filter { event: Event -> event.localDate == localDate }
    }

    fun remove(event: Event) {
        events.remove(event)
    }

}