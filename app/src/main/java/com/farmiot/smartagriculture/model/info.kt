package com.farmiot.smartagriculture.model

data class Info(
    var controller: Controller? = null,
    var manual: Manual? = null,
    var weather: Weather? = null,
    var weatherReport: List<Weather>? = null
)