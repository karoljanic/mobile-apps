package com.example.calendar

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.calendar.event.Event
import com.example.calendar.event.EventViewModel
import com.example.calendar.event.EventsRepository
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class NewEventActivity : AppCompatActivity() {
    private lateinit var nameField: TextInputEditText
    private lateinit var timeField: TextInputEditText

    private lateinit var eventViewModel: EventViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        timeField = findViewById(R.id.timeField)
        nameField = findViewById(R.id.nameField)

        timeField.setOnClickListener {
            val timePickerBuilder: MaterialTimePicker.Builder = MaterialTimePicker
                .Builder()
                .setTitleText("Select a time")
                .setHour(2)
                .setMinute(30)
                .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            val timePicker = timePickerBuilder.build()
            timePicker.show(supportFragmentManager, "TIME_PICKER")

            timePicker.addOnPositiveButtonClickListener {
                timeField.setText("${timePicker.hour}:${timePicker.minute}")
            }
        }

        eventViewModel = ViewModelProvider(this)[EventViewModel::class.java]
    }

    fun createEvent(view: View) {
        if(!nameField.text.isNullOrBlank() && !timeField.text.isNullOrBlank()) {
            val name = nameField.text.toString()

            val dateString = intent.getStringExtra("date")!!
            val date = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE)

            val timeString = timeField.text.toString()
            val time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("H:m"))

            eventViewModel.insertEvent(Event(name,  date, time, 0))

            finish()
        }
    }
}