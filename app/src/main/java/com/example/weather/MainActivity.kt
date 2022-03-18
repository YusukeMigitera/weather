package com.example.weather

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.model.ClimateByDay
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadClimate() // 取得

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MyAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val textView = findViewById<TextView>(R.id.textView)
        viewModel.climate.observe(this) {
            textView.setText(viewModel.climate.value?.cod)
            adapter.climate30Days = it.list
            adapter.notifyDataSetChanged()
        }
    }
}

private class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    var climate30Days: List<ClimateByDay> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val climate = climate30Days[position]

//        val zoneDt = Date(forecast.dt *1000L)
//        val dayList = zoneDt.toString().split(" ")
//        val formatDate = "${dayList[2]} (${dayList[0]})"

        val instant = Instant.ofEpochSecond(climate.dt) // ofEpochSecond() 26
        val fmt = DateTimeFormatter.ofPattern("dd(E)")
        val zone = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
        val formatDate = zone.format(fmt)

        holder.day.text = formatDate
        holder.max.text = "%.1f℃".format(climate.temp.average_max - 273.15)
        holder.min.text = "%.1f℃".format(climate.temp.average_min - 273.15)
        holder.humidity.text = "${climate.humidity.toInt()}%"
        holder.wind.text = "${climate.wind_speed}m/s"
        holder.itemView.setBackgroundColor(
            if (position % 2 == 0) {
                Color.WHITE
            } else {
                Color.LTGRAY
            }
        )
    }

    override fun getItemCount(): Int {
        return climate30Days.size
    }
}

private class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val day = itemView.findViewById<TextView>(R.id.day)
    val max = itemView.findViewById<TextView>(R.id.max)
    val min = itemView.findViewById<TextView>(R.id.min)
    val humidity = itemView.findViewById<TextView>(R.id.humidity)
    val wind = itemView.findViewById<TextView>(R.id.wind)
}