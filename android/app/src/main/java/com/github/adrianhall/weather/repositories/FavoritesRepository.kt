package com.github.adrianhall.weather.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.adrianhall.weather.models.FavoriteCity
import timber.log.Timber

class FavoritesRepository(context: Context) {
    private var mFavorites: MutableLiveData<List<FavoriteCity>> = MutableLiveData()
    var favoriteCities: LiveData<List<FavoriteCity>> = mFavorites

    init {
        loadCities()
    }

    /**
     * Load the list of favorite cities from the backing store, posting it to the favoriteCities
     * observable live data object.
     */
    private fun loadCities() {
        Timber.d("Loading cities from backing store")
    }

    /**
     * Saves the list of favorite cities to the backing store.  no need to post it back to the
     * list, but you should ensure only one "save" operation is running at any given time.
     */
    private fun saveCities() {
        Timber.d("Saving cities to backing store")
    }

    /**
     * Add an item to the favorites list
     */
    fun addCity(city: FavoriteCity) {
        // Check to see if the location already exists
        if (cityIsFavorite(city)) {
            return
        }

        // Construct the new list
        val cityList = ArrayList<FavoriteCity>()
        favoriteCities.value?.let { cityList.addAll(it) }
        cityList.add(city)

        // Post the new list to the observable
        mFavorites.postValue(cityList)
    }

    /**
     * Remove an item from the favorites list
     */
    fun removeCity(city: FavoriteCity) {
        // Check to see if the location exists
        if (!cityIsFavorite(city)) {
            return
        }

        // Construct the new list
        val cityList = favoriteCities.value?.filter { fave -> fave.toLocationString() != city.toLocationString() }

        // Post the new list to the observable
        mFavorites.postValue(cityList)
    }

    /**
     * Returns true if the provided city is a favorite already
     */
    fun cityIsFavorite(city: FavoriteCity): Boolean {
        val f = favoriteCities.value?.filter { fave -> fave.toLocationString() == city.toLocationString() }
        return f?.isNotEmpty() ?: false
    }
}