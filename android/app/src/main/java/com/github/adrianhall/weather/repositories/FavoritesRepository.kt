package com.github.adrianhall.weather.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.adrianhall.weather.models.FavoriteCity
import timber.log.Timber

class FavoritesRepository(context: Context) {
    companion object {
        private var PREFS_FILE = FavoritesRepository::class.java.canonicalName + ".prefs"
    }
    private val mapper = ObjectMapper().registerKotlinModule()
    private val preferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
    private var mFavorites: MutableLiveData<List<FavoriteCity>> = MutableLiveData()
    var favoriteCities: LiveData<List<FavoriteCity>> = mFavorites

    init {
        loadCities()
    }

    /**
     * Load the list of favorite cities from the backing store, posting it to the favoriteCities
     * observable live data object.
     *
     * It's ok to do this in an async method since we are posting to the observable.
     */
    private fun loadCities() {
        Timber.d("BEGIN: Loading cities from backing store")
        val jsonStr = preferences.getString("cities", "")
        if (jsonStr != null && jsonStr != "") {
            Timber.d("Loaded some cities - converting to objects")
            val cities = mapper.readValue<List<FavoriteCity>>(jsonStr)
            mFavorites.postValue(cities)
        } else {
            Timber.d("No cities found - adding some temporary test ones")
            val seattle = FavoriteCity(47.60357, -122.32945, "Seattle, USA")
            val london = FavoriteCity(51.509865, -0-0.118092, "London, UK")
            mFavorites.postValue(listOf(seattle, london))
        }
        Timber.d("END: Loading cities from backing store")
    }

    /**
     * Saves the list of favorite cities to the backing store.  no need to post it back to the
     * list, but you should ensure only one "save" operation is running at any given time.
     */
    private fun saveCities(cities: List<FavoriteCity>) {
        Timber.d("BEGIN: Saving cities to backing store")
        val jsonStr = mapper.writeValueAsString(cities)
        Timber.d("JSON = $jsonStr")
        preferences.edit().putString("cities", jsonStr).apply()
        Timber.d("END: Saving cities to backing store")
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

        // Save to disk
        saveCities(cityList)

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
        val cityList = ArrayList<FavoriteCity>()
        val filteredList = favoriteCities.value?.filter { fave -> fave.toLocationString() != city.toLocationString() }
        filteredList?.let { cityList.addAll(it) }

        // Save to disk
        saveCities(cityList)

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