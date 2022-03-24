package com.example.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.model.ForecastBy3h
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class HorizontalAdapter : RecyclerView.Adapter<HorizontalAdapter.HorizontalViewHolder>() {

    var forecasts5Days: List<ForecastBy3h> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.horizontal_item, parent, false)
        return HorizontalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalViewHolder, position: Int) {
        val forecast = forecasts5Days[position]

        val instant = Instant.ofEpochSecond(forecast.dt) // ofEpochSecond() 26
        val fmtDate = DateTimeFormatter.ofPattern("dd(E)")
        val fmtTime = DateTimeFormatter.ofPattern("HH:mm")
        val zone = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatDate = zone.format(fmtDate)
        val formatTime = zone.format(fmtTime)

        holder.date.text = formatDate
        holder.time.text = formatTime
        holder.temp.text = "%.1f℃".format(forecast.main.temp - 273.15)
        Picasso.get().load("https://openweathermap.org/img/w/${forecast.weather[0].icon}.png")
            .into(holder.icon)
        holder.humidity.text = forecast.main.humidity.toString() + "%"
        holder.wind.text = "${forecast.wind.speed}m/s"
    }

    override fun getItemCount(): Int {
        return forecasts5Days.size
    }

    class HorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.date)
        val time = itemView.findViewById<TextView>(R.id.time)
        val temp = itemView.findViewById<TextView>(R.id.temp)
        val icon = itemView.findViewById<ImageView>(R.id.icon)
        val humidity = itemView.findViewById<TextView>(R.id.humidity)
        val wind = itemView.findViewById<TextView>(R.id.wind)
    }
}
