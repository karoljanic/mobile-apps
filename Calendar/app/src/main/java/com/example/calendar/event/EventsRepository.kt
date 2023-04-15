package com.example.calendar.event

import java.time.LocalDate
import java.time.LocalTime

object EventsRepository {
    private lateinit var events: ArrayList<Event>
    private var idCounter: Int = 0

    fun initialize() {
        events = ArrayList()
        idCounter = 0
    }

    fun add(name: String, localDate: LocalDate, localTime: LocalTime) {
        events.add(Event(idCounter, name, localDate, localTime, 0))
        idCounter++
    }

    fun updateRating(id: Int, newRating: Int) {
        events[id].rating = newRating
    }

    fun get(localDate: LocalDate): List<Event> {
        return events.filter { event: Event -> event.localDate == localDate }
    }

    fun remove(event: Event) {
        events.remove(event)
    }

    fun remove(id: Int) {
        events.removeAt(id)
    }
}