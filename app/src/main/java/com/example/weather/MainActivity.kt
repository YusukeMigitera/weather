package com.example.weather

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.model.ForecastBy3h
import java.util.*

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadForecast() // 取得

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MyAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val textView = findViewById<TextView>(R.id.textView)
        viewModel.forecast.observe(this) {
            textView.setText(viewModel.forecast.value?.cod)
            adapter.forecastBy3hList = it.list
            adapter.notifyDataSetChanged()
        }
    }
}

private class MyAdapter : RecyclerView.Adapter<MyViewHolder>() {

    var forecastBy3hList: List<ForecastBy3h> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val forecast = forecastBy3hList[position]

        val zoneDt = Date(forecast.dt *1000L)
        val dayList = zoneDt.toString().split(" ")
        val formatDate = "${dayList[2]} (${dayList[0]})"

        holder.day.text = formatDate
        holder.max.text = "%.1f℃".format(forecast.temp.average_max - 273.15)
        holder.min.text = "%.1f℃".format(forecast.temp.average_min - 273.15)
        holder.humidity.text = "${forecast.humidity.toInt()}%"
        holder.wind.text = "${forecast.wind_speed}m/s"
        holder.itemView.setBackgroundColor(
            if (position % 2 == 0) {
                Color.WHITE
            } else {
                Color.LTGRAY
            }
        )
    }

    override fun getItemCount(): Int {
        return forecastBy3hList.size
    }
}

private class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val day = itemView.findViewById<TextView>(R.id.day)
    val max = itemView.findViewById<TextView>(R.id.max)
    val min = itemView.findViewById<TextView>(R.id.min)
    val humidity = itemView.findViewById<TextView>(R.id.humidity)
    val wind = itemView.findViewById<TextView>(R.id.wind)
}