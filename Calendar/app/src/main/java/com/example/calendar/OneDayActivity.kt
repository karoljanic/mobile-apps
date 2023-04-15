package com.example.calendar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.calendar.CalendarUtils
import com.example.calendar.event.Event
import com.example.calendar.event.EventsRepository
import com.example.calendar.events_list.EventsListAdapter
import com.example.calendar.events_list.EventsListRecyclerViewInterface
import com.google.android.material.textview.MaterialTextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class OneDayActivity : AppCompatActivity(), EventsListRecyclerViewInterface {
    private lateinit var eventsListRecyclerView: RecyclerView
    private lateinit var dayTitleText: MaterialTextView

    private lateinit var date: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one_day)

        eventsListRecyclerView = findViewById(R.id.eventsListRecyclerView)
        dayTitleText = findViewById(R.id.dayTitleText)

        val dateString: String = intent.getStringExtra("date")!!
        date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)

        dayTitleText.text = CalendarUtils.dayMonthYearFromDate(date)

        setEventsListView()
    }

    private fun setEventsListView() {
        val events: ArrayList<Event> = EventsRepository.get(date) as ArrayList<Event>
        val eventsListAdapter = EventsListAdapter(events, this)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        eventsListRecyclerView.layoutManager = layoutManager
        eventsListRecyclerView.adapter = eventsListAdapter
    }

    override fun onClickPosition(position: Int, event: Event) {
    }

    fun addEvent(view: View) {
        val intent: Intent = Intent(this@OneDayActivity, NewEventActivity::class.java)

        intent.putExtra("date", date.toString())

        startActivity(intent)
    }
}