package com.example.currentweatherdatabinding

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

data class WindData(val speed: Float, val dir: String)

class WindDataDeserializer : JsonDeserializer<WindData> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): WindData {
        json as JsonObject

        val dir = when (json.get("deg").asFloat) {
            in 11.25f..33.75f -> "NNE"
            in 33.75f..56.25f -> "NE"
            in 56.25f..78.75f -> "ENE"
            in 78.75f..101.25f -> "E"
            in 101.25f..123.75f -> "ESE"
            in 123.75f..146.25f -> "SE"
            in 146.25f..168.75f -> "SSE"
            in 168.75f..191.25f -> "S"
            in 191.25f..213.75f -> "SSW"
            in 213.75f..236.25f -> "SW"
            in 236.25f..258.75f -> "WSW"
            in 258.75f..281.25f -> "W"
            in 281.25f..303.75f -> "WNW"
            in 303.75f..326.25f -> "NW"
            in 326.25f..348.75f -> "NNW"
            else -> "N"
        }

        return WindData(
            json.get("speed").asFloat,
            dir
        )
    }

}
