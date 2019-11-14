package com.github.adrianhall.weather.services

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.models.Weather
import okhttp3.*
import timber.log.Timber
import java.io.IOException


class WeatherService(context: Context) {
    companion object {
        private const val BASE_URI = "https://api.darksky.net/forecast"
    }

    private val apiKey = context.getString(R.string.dark_sky_api_key)
    private val client = OkHttpClient()
    private val mapper = ObjectMapper().registerKotlinModule()

    /**
     * Get the current weather at a location
     */
    fun getWeatherForecast(latitude: Double, longitude: Double, callback: (Weather?, Exception?) -> Unit) {
        Timber.d("getWeatherForecast... url = ${getUri(latitude, longitude)}")
        val request = Request.Builder()
            .url(getUri(latitude, longitude))
            .build()
        Timber.d("Calling OkHttp")

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Timber.e(e)
                callback.invoke(null, e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Timber.e("Response was not successful")
                    callback.invoke(null, RuntimeException("Unexpected response: ${response.code}"))
                } else {
                    Timber.d("RESPONSE FROM DARK SKY API:")
                    for ((name, value) in response.headers) {
                        Timber.d("$name: $value")
                    }
                    val body: String? = response.body?.string()
                    if (body != null) {
                        try {
                            val weather = mapper.readValue(body, Weather::class.java)
                            callback.invoke(weather, null)
                        } catch (error: Exception) {
                            callback.invoke(null, error)
                        }
                    } else {
                        callback.invoke(null, RuntimeException("Unexpected response: No body received"))
                    }
                }
            }
        })
    }

    /**
     * Returns the actual URI that we need to do a GET on to retrieve the weather report
     */
    private fun getUri(latitude: Double, longitude: Double): String
        = "%s/%s/%.5f,%.5f?exclude=minutely,flags&lang=en&units=si".format(BASE_URI, apiKey, latitude, longitude)
}