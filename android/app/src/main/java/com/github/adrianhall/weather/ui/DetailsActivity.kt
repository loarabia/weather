package com.github.adrianhall.weather.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.models.FavoriteCity
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

    /**
     * Android lifecycle - called when the activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if (intent.hasExtra("latitude") && intent.hasExtra("longitude") && intent.hasExtra("displayName")) {
            latitude = intent.getDoubleExtra("latitude", 0.0)
            longitude = intent.getDoubleExtra("longitude", 0.0)
            displayName = intent.getStringExtra("displayName") ?: "Unknown"
        } else {
            Timber.e("Not all information was provided!")
            finish()
        }

        // Now we can set up the details for this page.
    }
}
