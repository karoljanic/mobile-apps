package com.example.calendar

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView

class CalendarViewHolder(view: View, private val recyclerViewInterface: RecyclerViewInterface) : RecyclerView.ViewHolder(view) {
    var dayOfMonth: MaterialTextView = view.findViewById(R.id.cellDayText)

    init {
        view.setOnClickListener(View.OnClickListener {
            if(adapterPosition != RecyclerView.NO_POSITION) {
                recyclerViewInterface.onClickPosition(adapterPosition)
            }
        })
    }
}