package com.ndroid.weatherapp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var editCityName: EditText
    lateinit var btnSearch: Button
    lateinit var imageWeather: ImageView
    lateinit var tvTemperature: TextView
    lateinit var tvCityName: TextView

    lateinit var layoutWeather: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCityName = findViewById(R.id.editCity)
        btnSearch = findViewById(R.id.btnSearch)
        imageWeather = findViewById(R.id.imageWeather)
        tvCityName = findViewById(R.id.tvCityName)
        tvTemperature = findViewById(R.id.tvTemperature)
        layoutWeather = findViewById(R.id.layoutWeather)



        // TODO1: create retrofit instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherService::class.java)

        // TODO2: call weather api
        val result = weatherService.getWeatherByCity()

        result.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if(response.isSuccessful) {
                    val result = response.body()
                    val main = result?.get("main")?.asJsonObject
                    val temp = main?.get("temp")?.asDouble
                    val cityName = result?.get("name")?.asString

                    val weather = result?.get("weather")?.asJsonArray
                    val icon = weather?.get(0)?.asJsonObject?.get("icon")?.asString

                    Picasso.get().load("https://openweathermap.org/img/w/$icon.png").into(imageWeather)

                    tvTemperature.text = "$temp °C"
                    tvCityName.text = cityName

                    layoutWeather.visibility = View.VISIBLE

                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
               Toast.makeText(applicationContext, "Erreur serveur", Toast.LENGTH_SHORT).show()
            }

        })

    }

}