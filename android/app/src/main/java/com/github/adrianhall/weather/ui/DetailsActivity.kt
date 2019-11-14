package com.github.adrianhall.weather.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.models.FavoriteCity
import com.github.adrianhall.weather.models.Weather
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.alert
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Displays the details for a single favorite city.  The city is passed in as a set of three
 * items in extras - latitude, longitude, display-name
 */
class DetailsActivity : AppCompatActivity() {
    companion object {
        /**
         * Static method for starting "this" activity with the right information.
         */
        fun startActivity(activity: Activity, city: FavoriteCity) {
            val intent = Intent(activity, DetailsActivity::class.java).apply {
                putExtra("latitude", city.latitude)
                putExtra("longitude", city.longitude)
                putExtra("displayName", city.displayName)
            }
            activity.startActivity(intent)
        }
    }

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var displayName: String = "Unknown"
    private var isFavorite: Boolean = false

    private val vm by viewModel<DetailsViewModel>()

    /**
     * Android lifecycle - called when the activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setSupportActionBar(details_toolbar)

        if (intent.hasExtra("latitude") && intent.hasExtra("longitude") && intent.hasExtra("displayName")) {
            latitude = intent.getDoubleExtra("latitude", 0.0)
            longitude = intent.getDoubleExtra("longitude", 0.0)
            displayName = intent.getStringExtra("displayName") ?: "Unknown"
        } else {
            Timber.e("Not all information was provided!")
            finish()
        }

        // Now we can set up the details for this page.
        supportActionBar?.apply {
            title = displayName
            setDisplayHomeAsUpEnabled(true)
        }

        // Set up the favorite on / off indicator
        isFavorite = vm.isFavorite(latitude, longitude)
        if (isFavorite) {
            details_favorite_button.setImageResource(R.drawable.icon_is_favorite)
        }
        details_favorite_button.setOnClickListener { view -> handleFavoriteClick(view) }

        // Finally, get the weather and display it.
        vm.getWeatherForecast(latitude, longitude) { weather, exception ->
            when {
                exception != null -> {
                    Timber.e(exception)
                    handleErrorAlert(exception.message ?: "Unknown error")
                }
                weather == null -> {
                    Timber.e("No exception, but no content either")
                    handleErrorAlert("No weather data received")
                }
                else -> updateUI(weather)
            }
        }
    }

    /**
     * Handles the click from the image button.
     */
    private fun handleFavoriteClick(view: View) {
        Timber.d("Clicked on details favorite button")
        if (isFavorite) {
            Timber.d("Removing from favorites")
            (view as ImageButton).setImageResource(R.drawable.icon_not_favorite)
            vm.removeCity(FavoriteCity(latitude, longitude, displayName))
        } else {
            Timber.d("Adding to favorites")
            (view as ImageButton).setImageResource(R.drawable.icon_is_favorite)
            vm.addCity(FavoriteCity(latitude, longitude, displayName))
        }
    }

    /**
     * Displays an error message, then returns to the previous page
     */
    private fun handleErrorAlert(errorMessage: String) {
        alert {
            title = "Error receiving weather"
            message = errorMessage
            positiveButton(android.R.string.ok) {
                this@DetailsActivity.finish()
            }
        }
    }

    /**
     * Updates the UI for the weather return value
     */
    private fun updateUI(weather: Weather) {
        Timber.d("Got Weather: ${weather.currently.summary}")

        runOnUiThread {
            // Alerts icon
            if (weather.alerts != null && weather.alerts.isNotEmpty()) {
                details_alerts_indicator.setImageResource(R.drawable.icon_warning)
                details_alerts_indicator.setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
            } else {
                details_alerts_indicator.visibility = View.INVISIBLE
            }

            // Current conditions
            weather_current_conditions_icon.setImageResource(weatherIcon(weather.currently.icon))
            weather_current_temperature.text = "%.0f°C".format(weather.currently.temperature)
            weather_current_summary.text = weather.currently.summary
        }
    }

    /**
     * Converts the weather icon provided by Dark Sky API to a drawable reference
     */
    private fun weatherIcon(icon: String): Int = when(icon) {
        "clear-day" -> R.drawable.weather_clear_day
        "clear-night" -> R.drawable.weather_clear_night
        "cloudy" -> R.drawable.weather_cloudy
        "fog" -> R.drawable.weather_fog
        "hail" -> R.drawable.weather_hail
        "partly-cloudy-day" -> R.drawable.weather_partly_cloudy_day
        "partly-cloudy-night" -> R.drawable.weather_partly_cloudy_night
        "rain" -> R.drawable.weather_rain
        "sleet" -> R.drawable.weather_sleet
        "snow" -> R.drawable.weather_snow
        "thunderstorm" -> R.drawable.weather_thunderstorm
        "tornado" -> R.drawable.weather_tornado
        "wind" -> R.drawable.weather_wind
        else -> R.drawable.weather_unknown
    }
}
