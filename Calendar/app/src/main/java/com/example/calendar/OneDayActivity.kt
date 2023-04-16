package com.example.calendar

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.calendar.CalendarUtils
import com.example.calendar.event.Event
import com.example.calendar.event.EventsRepository
import com.example.calendar.events_list.EventsListAdapter
import com.example.calendar.events_list.EventsListRecyclerViewInterface
import com.google.android.material.textview.MaterialTextView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class OneDayActivity : AppCompatActivity(), EventsListRecyclerViewInterface {
    private lateinit var eventsListRecyclerView: RecyclerView
    private lateinit var dayTitleText: MaterialTextView

    private lateinit var date: LocalDate

    private var alarmManager: AlarmManager? = null

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

    override fun onRestart() {
        super.onRestart()

        setEventsListView()
    }

    override fun onResume() {
        super.onResume()

        setEventsListView()
    }

    private fun setEventsListView() {
        val events: ArrayList<Event> = EventsRepository.get(date) as ArrayList<Event>
        val eventsListAdapter = EventsListAdapter(
            events, this, resources.configuration.orientation
        )

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
        eventsListRecyclerView.layoutManager = layoutManager
        eventsListRecyclerView.adapter = eventsListAdapter
    }

    override fun onClickPosition(position: Int, event: Event) {

    }

    override fun updateEventRating(position: Int, event: Event, newRating: Int) {
        EventsRepository.updateRating(event, newRating)
        eventsListRecyclerView.adapter?.notifyItemChanged(position)
    }

    override fun removeEvent(position: Int, event: Event) {
        EventsRepository.remove(event)
        eventsListRecyclerView.adapter?.notifyItemRemoved(position)
    }

    override fun setEventReminder(position: Int, event: Event) {
        if (alarmManager == null) {
            alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        }

        val intent: Intent = Intent(this@OneDayActivity, ReminderReceiver::class.java)
        intent.putExtra("title", "Event ${event.name} Is Coming Up!")
        intent.putExtra("content", "Go To Calendar App To View Details")

        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(this@OneDayActivity, 0, intent, 0)
        val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        val localDateTime = LocalDateTime.of(event.localDate, event.localTime)
        val zonedDateTime = localDateTime.atZone(ZoneId.systemDefault())
        val calendar = Calendar.getInstance()
        calendar.time = Date.from(zonedDateTime.toInstant())

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

        Toast.makeText(this, "Alarm Set Successfully", Toast.LENGTH_SHORT).show()
    }

    fun addEvent(view: View) {
        val intent: Intent = Intent(this@OneDayActivity, NewEventActivity::class.java)

        intent.putExtra("date", date.toString())

        startActivity(intent)
    }
}