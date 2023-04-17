package com.example.calendar.events_list

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.example.calendar.calendar.CalendarUtils
import com.example.calendar.event.Event
import com.example.calendar.event.EventsRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class EventsListAdapter(
    private val eventsListRecyclerViewInterface: EventsListRecyclerViewInterface,
    private val date: LocalDate
) : RecyclerView.Adapter<EventsListViewHolder>() {

    private var events = emptyList<Event>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsListViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.event_line, parent, false)

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

        holder.eventRating.setOnRatingBarChangeListener { _, rating, _ ->
            eventsListRecyclerViewInterface.updateEventRating(position, event, rating.toInt())
        }

        holder.deleteEvent.setOnClickListener {
            eventsListRecyclerViewInterface.removeEvent(position, event)
        }

        holder.reminderEvent.setOnClickListener {
            eventsListRecyclerViewInterface.setEventReminder(position, event)
        }

        if(position % 2 == 0)
            holder.eventCard.setBackgroundResource(R.color.davy_s_gray)
        else
            holder.eventCard.setBackgroundResource(R.color.taupe)
    }

    fun setData(eventsList: List<Event>) {
        events = eventsList.filter { event: Event -> event.localDate == date }
        notifyDataSetChanged()
    }
}