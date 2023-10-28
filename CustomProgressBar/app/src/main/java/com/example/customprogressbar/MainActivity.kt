package com.example.customprogressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.slider.Slider

class MainActivity : AppCompatActivity() {
    private lateinit var customProgressBar: CustomProgressBar
    private lateinit var slider: Slider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.customProgressBar = findViewById(R.id.customProgressBar)
        this.slider = findViewById(R.id.slider)

        this.slider.addOnChangeListener { _, value, _ ->
            this.customProgressBar.progress = value
        }
    }
}