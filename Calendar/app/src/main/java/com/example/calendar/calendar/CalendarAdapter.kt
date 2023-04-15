package com.example.calendar.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import java.time.LocalDate
import kotlin.math.roundToInt

class CalendarAdapter(
    private val daysOfMonth: ArrayList<LocalDate?>,
    private val calendarRecyclerViewInterface: CalendarRecyclerViewInterface
) : RecyclerView.Adapter<CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.calendar_cell, parent, false)

        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height / 6.0).roundToInt()

        return CalendarViewHolder(view, calendarRecyclerViewInterface, daysOfMonth)
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val localDate: LocalDate? = daysOfMonth[position]

        if(localDate == null)
            holder.dayOfMonthText.text = ""
        else {
            holder.dayOfMonthText.text = localDate.dayOfMonth.toString()
            if(localDate == CalendarUtils.currentDate) {
                holder.dayOfMonthLayout.setBackgroundResource(R.color.mint)
            }
        }

    }
}