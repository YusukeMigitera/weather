package com.example.weather

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.model.ClimateByDay
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class VerticalAdapter : RecyclerView.Adapter<VerticalAdapter.VerticalViewHolder>() {

    var climate30Days: List<ClimateByDay> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return VerticalViewHolder(view)
    }

    override fun onBindViewHolder(holder: VerticalViewHolder, position: Int) {
        holder.set(climate30Days[position])

//        val zoneDt = Date(forecast.dt *1000L)
//        val dayList = zoneDt.toString().split(" ")
//        val formatDate = "${dayList[2]} (${dayList[0]})"


    }

    override fun getItemCount(): Int = climate30Days.size


    class VerticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val day = itemView.findViewById<TextView>(R.id.day)
        private val max = itemView.findViewById<TextView>(R.id.max)
        private val min = itemView.findViewById<TextView>(R.id.min)
        private val humidity = itemView.findViewById<TextView>(R.id.humidity)
        private val wind = itemView.findViewById<TextView>(R.id.wind)

        fun set(climate: ClimateByDay) {
            val instant = Instant.ofEpochSecond(climate.dt) // ofEpochSecond() 26
            val fmt = DateTimeFormatter.ofPattern("dd(E)")
            val zone = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatDate = zone.format(fmt)

            day.text = formatDate
            max.text = String.format("%.1f℃", climate.temp.average_max - 273.15)
            min.text = String.format("%.1f℃", climate.temp.average_min - 273.15)
            humidity.text = String.format("%d%s", climate.humidity.toInt(),"%")
            wind.text = String.format("%.2f m/s", climate.wind_speed)
            itemView.setBackgroundColor(
                if (position % 2 == 0) {
                    Color.LTGRAY
                } else {
                    Color.WHITE
                }
            )
        }
    }
}