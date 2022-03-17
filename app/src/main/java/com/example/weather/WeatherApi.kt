package com.example.weather

import android.net.Uri
import com.example.weather.model.Forecast
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request

class WeatherApi {
    companion object {
        private const val API_ENDPOINT = "https://community-open-weather-map.p.rapidapi.com/"
        private val CLIENT = OkHttpClient()
        private val MOSHI = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun getForecast(): Forecast {
        val uri = Uri.parse(API_ENDPOINT).buildUpon()
            .appendPath("climate/month?q=San%20Francisco")
        val request = Request.Builder().url(uri.toString()).build()
        val response = CLIENT.newCall(request).execute()
        val json = response.body?.string() ?: ""

        return MOSHI.adapter(Forecast::class.java).fromJson(json)!!
    }
}