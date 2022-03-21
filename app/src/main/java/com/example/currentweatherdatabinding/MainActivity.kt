package com.example.currentweatherdatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.currentweatherdatabinding.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import java.util.*
import kotlin.math.absoluteValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var city: String = ""
    private var time: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        findViewById<Button>(R.id.update_btn)
            .setOnClickListener {
                if (binding.inputCity.text.isNotEmpty()) {
                    city = binding.inputCity.text.toString()

                    onClick(it)

                } else {
                    Toast
                        .makeText(this, "Введите город!", Toast.LENGTH_LONG)
                        .show()
                }
            }
    }
    suspend fun loadWeather() {
        var weather: Weather? = null

        try {
            val API_KEY = resources.getString(R.string.API_KEY)

            val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY&units=metric";
            val stream = URL(weatherURL).getContent() as InputStream
            // JSON отдаётся одной строкой,
            val data = Scanner(stream).nextLine()

            weather = GsonBuilder()
                .registerTypeAdapter(Weather::class.java, WeatherDeserializer())
                .create()
                .fromJson(data, Weather::class.java)

            Log.d("mytag", weather.toString())

            if (weather?.cod == 200) {
                binding.weather = weather
            } else {
            }
        } catch (e: Exception) {
            Toast
                .makeText(this, "Нет интернета!", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun onClick(v: View) {
        // Используем IO-диспетчер вместо Main (основного потока)
        GlobalScope.launch (Dispatchers.IO) {
            loadWeather()
        }
    }
}