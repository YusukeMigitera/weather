package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadForecast()

        val textView = findViewById<TextView>(R.id.textView)
        viewModel.forecast.observe(this) {
            textView.setText(viewModel.forecast.value?.cod)
        }
    }
}