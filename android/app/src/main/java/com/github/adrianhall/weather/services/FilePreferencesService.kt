package com.github.adrianhall.weather.services

import android.content.Context
import timber.log.Timber

/**
 * Implementation of the Storage Service which loads and saves
 */
class FilePreferencesService(context: Context): StorageService {
    companion object {
        private var PREFS_FILE = FilePreferencesService::class.java.canonicalName + ".prefs"
    }
    private val preferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)

    /**
     * Asynchronous loading of data.  Exception is null if there is no error.
     * Data is null if there is no error, and no data either.
     */
    override fun loadJson(accessToken: String, callback: (String?, Exception?) -> Unit) {
        Timber.d("Loading JSON")
        val json = preferences.getString("cities", null)
        callback.invoke(json, null)
    }

    /**
     * Asynchronous saving of data.  Exception is null if there is no error.
     */
    override fun saveJson(json: String, accessToken: String, callback: (Exception?) -> Unit) {
        Timber.d("Saving JSON")
        try {
            preferences.edit().putString("cities", json).apply()
            callback.invoke(null)
        } catch (error: Exception) {
            callback.invoke(error)
        }
    }
}