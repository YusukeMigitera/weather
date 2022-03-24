package com.example.weather

import androidx.lifecycle.MutableLiveData
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.weather.model.Climate
import com.example.weather.model.Forecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val forecast = MutableLiveData<Forecast>()
    val climate = MutableLiveData<Climate>()

    fun loadForecast() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    WeatherApi().getForecast()
                }
                forecast.value = response
            } catch (e: Exception) {
                Log.e("Forecast", "Error", e)
            }
        }
    }

    fun loadClimate() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    WeatherApi().getClimate()
                }
                climate.value = response
            } catch (e: Exception) {
                Log.e("Climate", "Error", e)
            }
        }
    }
}