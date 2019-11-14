package com.github.adrianhall.weather.ui

import androidx.lifecycle.ViewModel
import com.github.adrianhall.weather.models.FavoriteCity
import com.github.adrianhall.weather.models.Weather
import com.github.adrianhall.weather.repositories.FavoritesRepository
import com.github.adrianhall.weather.services.WeatherService

class DetailsViewModel(private val weatherService: WeatherService, private val favoritesRepository: FavoritesRepository) : ViewModel() {
    fun getWeatherForecast(latitude: Double, longitude: Double, callback: (Weather?, Exception?) -> Unit)
        = weatherService.getWeatherForecast(latitude, longitude, callback)

    fun isFavorite(latitude: Double, longitude: Double): Boolean
        = favoritesRepository.cityIsFavorite(FavoriteCity(latitude, longitude, ""))

    fun addCity(city: FavoriteCity) = favoritesRepository.addCity(city)
    fun removeCity(city: FavoriteCity) = favoritesRepository.removeCity(city)


}