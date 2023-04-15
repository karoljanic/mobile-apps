package com.example.calendar.events_list

import com.example.calendar.event.Event

interface EventsListRecyclerViewInterface {
    fun onClickPosition(position: Int, event: Event)
}