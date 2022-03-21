package com.example.currentweatherdatabinding

import java.util.*
import kotlin.math.absoluteValue

data class CoordData(
    val lon: Float,
    val lat: Float
) {
    fun longitudeToTime(): String {
        val d = 0.004167
        val v = lon / d

        val sign = (if (v < 0) "-" else "+") + "GMT"

        val hours = (v.absoluteValue / 3600).toInt()
        val minutes = String.format(Locale.US, "%.2f", (v.absoluteValue % 60))

        val time = "${hours.toString().padStart(2, '0')}:${minutes.padStart(5, '0')} " + sign

        return time
    }
}