package com.ndroid.weatherapp2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    companion object {
        const val API_KEY = "YOUR CODE HERE"
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/weather/"
    }

    @GET("?units=metric&appid=$API_KEY")
    fun getWeatherByCity(@Query("q") city: String): Call<WeatherResult>
}