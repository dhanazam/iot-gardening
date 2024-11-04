package com.farmiot.smartagriculture.model

data class Weather(
    var humidity: Int = 0,
    var soilMoisture: Int = 0,
    var temprature: Int = 0,
    var waterLevel: Int = 0
)