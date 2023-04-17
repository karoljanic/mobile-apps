package com.example.calendar.events_list

import android.util.Log
import com.example.calendar.event.Event
import android.view.View
import android.widget.RatingBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.slider.RangeSlider
import com.google.android.material.textview.MaterialTextView
import kotlin.coroutines.coroutineContext

class EventsListViewHolder(
    view: View,
    private val eventsListRecyclerViewInterface: EventsListRecyclerViewInterface,
    private val events: List<Event>
) : RecyclerView.ViewHolder(view) {

    var eventLayout: ConstraintLayout = view.findViewById(R.id.eventLayout)
    var eventCard: MaterialCardView = view.findViewById(R.id.eventTileCard)
    var eventTitle: MaterialTextView = view.findViewById(R.id.eventTitle)
    var eventTime: MaterialTextView = view.findViewById(R.id.eventTime)
    var eventRating: RatingBar = view.findViewById(R.id.eventRating)
    var deleteEvent: MaterialButton = view.findViewById(R.id.deleteEventButton)
    var reminderEvent: MaterialButton = view.findViewById(R.id.reminderEventButton)

    init {
        view.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                eventsListRecyclerViewInterface.onClickPosition(
                    adapterPosition,
                    events[adapterPosition]
                )
            }
        }
    }
}