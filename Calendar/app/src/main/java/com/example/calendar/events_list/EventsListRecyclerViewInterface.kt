package com.example.calendar.events_list

import com.example.calendar.event.Event

interface EventsListRecyclerViewInterface {
    fun onClickPosition(position: Int, event: Event)

    fun updateEventRating(position: Int, event: Event, newRating: Int)

    fun removeEvent(position: Int, event: Event)

    fun setEventReminder(position: Int, event: Event)
}