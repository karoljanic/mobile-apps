package com.example.calendar.events_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.calendar.CalendarUtils
import com.example.calendar.event.Event
import kotlin.math.roundToInt

class EventsListAdapter(
    private val events: ArrayList<Event>,
    private val eventsListRecyclerViewInterface: EventsListRecyclerViewInterface
) : RecyclerView.Adapter<EventsListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsListViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.event_line, parent, false)

        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height / 3.0).roundToInt()

        return EventsListViewHolder(view, eventsListRecyclerViewInterface, events)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventsListViewHolder, position: Int) {
        val event: Event = events[position]

        holder.eventTitle.text = event.name
        holder.eventTime.text = CalendarUtils.hoursMinutesFromTime(event.localTime)
        holder.eventRating.rating = event.rating.toFloat()

        if(position % 2 == 0)
            holder.eventCard.setBackgroundResource(R.color.davy_s_gray)
        else
            holder.eventCard.setBackgroundResource(R.color.taupe)
    }
}