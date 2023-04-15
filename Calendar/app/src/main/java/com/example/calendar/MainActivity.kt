package com.example.calendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.calendar.CalendarAdapter
import com.example.calendar.calendar.CalendarUtils
import com.example.calendar.calendar.CalendarRecyclerViewInterface
import com.example.calendar.event.EventsRepository
import com.google.android.material.textview.MaterialTextView
import java.time.LocalDate
import java.time.Period
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), CalendarRecyclerViewInterface {
    private lateinit var calendarRecyclerView : RecyclerView
    private lateinit var monthYearText : MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EventsRepository.initialize()
        CalendarUtils.currentDate = LocalDate.now()

        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearText)

        setMonthView()
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
}