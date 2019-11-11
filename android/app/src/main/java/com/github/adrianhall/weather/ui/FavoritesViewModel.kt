package com.github.adrianhall.weather.ui

import androidx.lifecycle.ViewModel
import com.github.adrianhall.weather.models.FavoriteCity
import com.github.adrianhall.weather.repositories.FavoritesRepository

class FavoritesViewModel(private val favoritesRepository: FavoritesRepository) : ViewModel() {
    val favoriteCities = favoritesRepository.favoriteCities

    fun addCity(city: FavoriteCity) = favoritesRepository.addCity(city)
    fun removeCity(city: FavoriteCity) = favoritesRepository.removeCity(city)
    fun cityIsFavorite(city: FavoriteCity): Boolean = favoritesRepository.cityIsFavorite(city)
}
