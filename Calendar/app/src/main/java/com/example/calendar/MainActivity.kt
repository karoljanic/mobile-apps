package com.example.calendar

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class MainActivity : AppCompatActivity(), RecyclerViewInterface {
    private lateinit var calendarRecyclerView : RecyclerView
    private lateinit var monthYearText : MaterialTextView
    private lateinit var currentDate: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        calendarRecyclerView = findViewById(R.id.calendarRecyclerView)
        monthYearText = findViewById(R.id.monthYearText)
        currentDate = LocalDate.now()

        setMonthView()
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(currentDate)
        val daysInMonth: ArrayList<String> = generateDaysInMonth(currentDate)

        val calendarAdapter = CalendarAdapter(daysInMonth, this)

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    private fun generateDaysInMonth(date: LocalDate): ArrayList<String> {
        val daysInMonthArray: ArrayList<String> = ArrayList()
        val yearMonth: YearMonth = YearMonth.from(date)
        val daysInMonth: Int = yearMonth.lengthOfMonth()
        val firstOfMonth: LocalDate = date.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String? {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
        return date.format(formatter)
    }

    fun previousMonth(view: View) {
        currentDate = currentDate.minusMonths(1)
        setMonthView()
    }

    fun nextMonth(view: View) {
        currentDate = currentDate.plusMonths(1)
        setMonthView()
    }

    override fun onClickPosition(position: Int) {
        Toast.makeText(this@MainActivity, "$position", Toast.LENGTH_LONG).show()
    }
}