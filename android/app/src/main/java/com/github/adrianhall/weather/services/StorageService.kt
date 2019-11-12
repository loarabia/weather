package com.github.adrianhall.weather.services

interface StorageService {
    /**
     * Asynchronous loading of data.  Exception is null if there is no error.
     */
    fun loadJson(callback: (String?, Exception?) -> Unit)

    /**
     * Asynchronous saving of data.  Exception is null if there is no error.
     */
    fun saveJson(json: String, callback: (Exception?) -> Unit)
}