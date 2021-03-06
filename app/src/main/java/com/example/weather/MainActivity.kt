package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadForecast()
        viewModel.loadClimate() // 取得

        val datetime = findViewById<TextView>(R.id.datetime)
        val icon = findViewById<ImageView>(R.id.icon)
        val weather = findViewById<TextView>(R.id.weather)
        val temperature = findViewById<TextView>(R.id.temperature)

        val horizontalRecyclerView = findViewById<RecyclerView>(R.id.horizontalScroll)
        val horizontalAdapter = HorizontalAdapter()
        horizontalRecyclerView.adapter = horizontalAdapter
        val lm = LinearLayoutManager(this)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        horizontalRecyclerView.layoutManager = lm

        viewModel.forecast.observe(this) {
            val instant =
                Instant.ofEpochSecond(viewModel.forecast.value?.list!![0].dt) // ofEpochSecond() 26
            val fmt = DateTimeFormatter.ofPattern("MM/dd(E) HH:mm")
            val zone = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatDate = zone.format(fmt)

            val temp = "%.1f℃".format(viewModel.forecast.value?.list!![0].main.temp - 273.15)

            datetime.text = formatDate
            Picasso.get()
                .load("https://openweathermap.org/img/w/${viewModel.forecast.value?.list!![0].weather[0].icon}.png")
                .into(icon)
            weather.text = viewModel.forecast.value?.list!![0].weather[0].main
            temperature.text = temp

            it.list.removeAt(0)
            horizontalAdapter.forecasts5Days = it.list
            horizontalAdapter.notifyItemRangeChanged(0, horizontalAdapter.itemCount)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = VerticalAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.climate.observe(this) {
            adapter.climate30Days = it.list
            adapter.notifyItemRangeChanged(0, adapter.itemCount)
        }
    }
}
