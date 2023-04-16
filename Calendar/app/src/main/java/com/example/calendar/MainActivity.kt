package com.example.calendar

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.calendar.CalendarAdapter
import com.example.calendar.calendar.CalendarRecyclerViewInterface
import com.example.calendar.calendar.CalendarUtils
import com.example.calendar.event.Event
import com.example.calendar.event.EventsRepository
import com.google.android.material.textview.MaterialTextView
import java.time.LocalDate
import java.time.Period


class MainActivity : AppCompatActivity(), CalendarRecyclerViewInterface {
    private lateinit var calendarRecyclerView : RecyclerView
    private lateinit var monthYearText : MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null) {
            val events: ArrayList<Event> = savedInstanceState.getParcelableArrayList("events")!!
            EventsRepository.initialize(events)
        }
        else
            EventsRepository.initialize()

        CalendarUtils.currentDate = LocalDate.now()

        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearText)

        setMonthView()

        createNotificationChannel()
    }

    override fun onRestart() {
        super.onRestart()

        setMonthView()
    }

    override fun onResume() {
        super.onResume()

        setMonthView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putParcelableArrayList("events", EventsRepository.getAll())
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val events: ArrayList<Event> = savedInstanceState.getParcelableArrayList("events")!!
        EventsRepository.initialize(events)

        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun setMonthView() {
        monthYearText.text = CalendarUtils.monthYearFromDate(CalendarUtils.currentDate)
        val daysInMonth: ArrayList<LocalDate?> = CalendarUtils.generateDaysInMonth(CalendarUtils.currentDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    fun previousMonth(view: View) {
        CalendarUtils.currentDate = CalendarUtils.currentDate.minus(Period.ofMonths(1))
        setMonthView()
    }

    fun nextMonth(view: View) {
        CalendarUtils.currentDate = CalendarUtils.currentDate.plus(Period.ofMonths(1))
        setMonthView()
    }

    override fun onClickPosition(position: Int, localDate: LocalDate?) {
        if(localDate != null) {
            val intent: Intent = Intent(this@MainActivity, OneDayActivity::class.java)

            intent.putExtra("date", localDate.toString())

            startActivity(intent)
        }
    }

    private fun createNotificationChannel() {
        val name: CharSequence = "CalendarReminderChannel"
        val description = "Channel For Reminder Manager"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("calendarapp", name, importance)
        channel.description = description
        val notificationManager = getSystemService(
            NotificationManager::class.java
        )
        notificationManager.createNotificationChannel(channel)
    }
}