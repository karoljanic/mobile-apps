package com.example.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class CalendarAdapter(
    private val daysOfMonth: ArrayList<String>,
    private val recyclerViewInterface: RecyclerViewInterface
) : RecyclerView.Adapter<CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.calendar_cell, parent, false)

        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        layoutParams.height = (parent.height / 6.0).roundToInt()

        return CalendarViewHolder(view, recyclerViewInterface)
    }

    override fun getItemCount(): Int {
        return daysOfMonth.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]
    }
}