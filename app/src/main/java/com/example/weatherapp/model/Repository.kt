package com.example.weatherapp.model

interface Repository {
    fun sentRequest()
    fun getResponse()
}