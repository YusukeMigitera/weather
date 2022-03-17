package com.example.weather

import androidx.lifecycle.MutableLiveData
import com.example.weather.model.Forecast

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val forecast = MutableLiveData<Forecast>()

    fun loadForecast() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    WeatherApi().getForecast()
                }
                forecast.value = response
            } catch (e: Exception) {
                Log.e("MixiLesson", "Error", e)
            }
        }
    }
}