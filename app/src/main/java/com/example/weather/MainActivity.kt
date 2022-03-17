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
        holder.fullName.text = position.toString()
        holder.description.text = forecast.humidity.toString()
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
    val fullName = itemView.findViewById<TextView>(R.id.fullName)
    val description = itemView.findViewById<TextView>(R.id.description)
}