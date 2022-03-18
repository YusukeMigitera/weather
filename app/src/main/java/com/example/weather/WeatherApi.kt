package com.example.weather

import android.net.Uri
import com.example.weather.model.Climate
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

    fun getClimate(): Climate {
        val uri = Uri.parse(API_ENDPOINT).buildUpon()
            .appendPath("climate")
            .appendPath("month")
            .appendQueryParameter("q", "San Francisco")
        val request = Request.Builder().url(uri.toString())
            .addHeader("x-rapidapi-key", "b60a4b9ff0mshff11b416225f2f7p1d1478jsn9e99bd91bca1")
            .addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
            .build()
        val response = CLIENT.newCall(request).execute()
        val json = response.body?.string() ?: ""

        return MOSHI.adapter(Climate::class.java).fromJson(json)!!
    }
}