package com.example.currentweatherdatabinding

import com.google.gson.*
import java.lang.reflect.Type

data class Weather(
    val weather: WeatherData,
    val main: MainData,
    val wind: WindData,
    val clouds: CloudsData,
    val sys: SysData,
    val timezone: Int,
    val coord: CoordData,
    val cod: Int
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}

class WeatherDeserializer : JsonDeserializer<Weather> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Weather {
        json as JsonObject

        val weatherData = json.get("weather").asJsonArray.first().toString()
        val main = json.get("main").asJsonObject.toString()
        val clouds = json.get("clouds").asJsonObject.toString()

        val sunrise = json.get("sys").asJsonObject.get("sunrise").asLong
        val sunset = json.get("sys").asJsonObject.get("sunset").asLong

        val coord = json.get("coord").asJsonObject

        val gson = Gson()
        val gsonBuilder = GsonBuilder()

       val wind = gsonBuilder
            .registerTypeAdapter(WindData::class.java, WindDataDeserializer())
            .create()
            .fromJson(json.get("wind").toString(), WindData::class.java)

        return Weather(
            gson.fromJson(weatherData, WeatherData::class.java),
            gson.fromJson(main, MainData::class.java),
            wind,
            gson.fromJson(clouds, CloudsData::class.java),
            SysData(sunrise, sunset),
            json.get("timezone").asInt,
            gson.fromJson(coord, CoordData::class.java),
            json.get("cod").asInt
        )
    }
}