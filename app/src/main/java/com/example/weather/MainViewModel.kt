package com.example.weather

import androidx.lifecycle.MutableLiveData
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.weather.model.Climate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val climate = MutableLiveData<Climate>()

    fun loadClimate() {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    WeatherApi().getClimate()
                }
                climate.value = response
            } catch (e: Exception) {
                Log.e("MixiLesson", "Error", e)
            }
        }
    }
}