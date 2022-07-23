package com.example.weatherapp.model

import com.example.weatherapp.model.details.Fact
import com.example.weatherapp.model.details.Forecast
import com.example.weatherapp.model.details.Info

data class WeatherDTO(
    val fact: Fact,
    val forecast: Forecast,
    val info: Info,
    val now: Int,
    val now_dt: String
)