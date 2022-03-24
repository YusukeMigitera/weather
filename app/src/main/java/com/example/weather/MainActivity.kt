package com.example.weather

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.model.ClimateByDay
import com.example.weather.model.ForecastBy3h
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

            datetime.setText(formatDate)
            Picasso.get()
                .load("https://openweathermap.org/img/w/${viewModel.forecast.value?.list!![0].weather[0].icon}.png")
                .into(icon)
            weather.setText(viewModel.forecast.value?.list!![0].weather[0].main)
            temperature.setText(temp)

            it.list.removeAt(0)
            horizontalAdapter.forecasts5Days = it.list
            horizontalAdapter.notifyItemRangeChanged(0, horizontalAdapter.itemCount)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = MyAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.climate.observe(this) {
            adapter.climate30Days = it.list
            adapter.notifyDataSetChanged()
        }
    }
}

private class HorizontalAdapter : RecyclerView.Adapter<HorizontalViewHolder>() {

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
}

private class HorizontalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val date = itemView.findViewById<TextView>(R.id.date)
    val time = itemView.findViewById<TextView>(R.id.time)
    val temp = itemView.findViewById<TextView>(R.id.temp)
    val icon = itemView.findViewById<ImageView>(R.id.icon)
    val humidity = itemView.findViewById<TextView>(R.id.humidity)
    val wind = itemView.findViewById<TextView>(R.id.wind)
}

private class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var climate30Days: List<ClimateByDay> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.set(climate30Days[position])

//        val zoneDt = Date(forecast.dt *1000L)
//        val dayList = zoneDt.toString().split(" ")
//        val formatDate = "${dayList[2]} (${dayList[0]})"


    }

    override fun getItemCount(): Int = climate30Days.size


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day = itemView.findViewById<TextView>(R.id.day)
        val max = itemView.findViewById<TextView>(R.id.max)
        val min = itemView.findViewById<TextView>(R.id.min)
        val humidity = itemView.findViewById<TextView>(R.id.humidity)
        val wind = itemView.findViewById<TextView>(R.id.wind)

        fun set(climate: ClimateByDay) {
            val instant = Instant.ofEpochSecond(climate.dt) // ofEpochSecond() 26
            val fmt = DateTimeFormatter.ofPattern("dd(E)")
            val zone = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            val formatDate = zone.format(fmt)
            val humi = "${climate.humidity.toInt()}%"

            day.text = formatDate
            max.text = "%.1f℃".format(climate.temp.average_max - 273.15)
            min.text = "%.1f℃".format(climate.temp.average_min - 273.15)
            humidity.text = String.format("%d%s", climate.humidity.toInt(),"%")
            wind.text = "${climate.wind_speed}m/s"
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