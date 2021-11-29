package com.ndroid.weatherapp2

import retrofit2.Call
import retrofit2.http.GET

interface WeatherService {

    @GET("?q=Paris&appid=915a276fe2f42f1255762165dd49adce&units=metric")
    fun getWeatherByCity(): Call<String>
}