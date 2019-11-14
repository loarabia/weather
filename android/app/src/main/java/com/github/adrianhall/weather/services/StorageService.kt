package com.github.adrianhall.weather.services

interface StorageService {
    /**
     * Asynchronous loading of data.  Exception is null if there is no error.
     * @param accessToken The access token for the user account
     */
    fun loadJson(accessToken: String, callback: (String?, Exception?) -> Unit)

    /**
     * Asynchronous saving of data.  Exception is null if there is no error.
     * @param json the JSON string to store
     * @param accessToken The access token for the user account
     */
    fun saveJson(json: String, accessToken: String, callback: (Exception?) -> Unit)
}