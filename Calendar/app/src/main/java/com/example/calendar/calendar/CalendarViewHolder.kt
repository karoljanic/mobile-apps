package com.example.calendar.calendar

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.calendar.R
import com.google.android.material.textview.MaterialTextView
import java.time.LocalDate

class CalendarViewHolder(
    view: View,
    private val calendarRecyclerViewInterface: CalendarRecyclerViewInterface,
    private val days: ArrayList<LocalDate?>
) : RecyclerView.ViewHolder(view) {

    var dayOfMonthLayout: ConstraintLayout = view.findViewById(R.id.cellLayout)
    var dayOfMonthText: MaterialTextView = view.findViewById(R.id.cellDayText)

    init {
        view.setOnClickListener {
            if (adapterPosition != RecyclerView.NO_POSITION) {
                calendarRecyclerViewInterface.onClickPosition(
                    adapterPosition,
                    days[adapterPosition]
                )
            }
        }
    }
}