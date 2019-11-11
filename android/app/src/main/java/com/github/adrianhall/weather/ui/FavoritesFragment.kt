package com.github.adrianhall.weather.ui

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.models.FavoriteCity
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavoritesFragment : Fragment(), TabFragment, LocationListener {
    private lateinit var favoritesAdapter: FavoritesAdapter
    private lateinit var locationManager: LocationManager
    private lateinit var geocoder: Geocoder
    private val vm by viewModel<FavoritesViewModel>()
    private var locationPermissionGranted: Boolean = true // Assume true, based on activity

    /**
     * Part of TabFragment - called to get the name of this tab.
     */
    override val title = context?.getString(R.string.tab_favorites) ?: "Favorites"

    /**
     * Android lifecycle - called when the fragment view is created
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        val view = inflater.inflate(R.layout.favorites_fragment, container, false)

        // Initialize the recylerview with no contents
        favoritesAdapter = FavoritesAdapter { entry ->
            Timber.d("Clicked on entry: ${entry.displayName}")
            // TODO: add navigation to the details page
        }
        val rv = view.findViewById<RecyclerView>(R.id.favorites_list)
        rv.adapter = favoritesAdapter

        vm.favoriteCities.observe(this, Observer { updateFavoritesAdapter() })

        // Initialize the location manager
        try {
            geocoder = Geocoder(context)
            locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5.0f, this)
        } catch (permissions: SecurityException) {
            Timber.e("Permission denied for location - setting flag")
            locationPermissionGranted = false
        }
        return view
    }

    /**
     * Updates the recycler view
     */
    private fun updateFavoritesAdapter() {
        // Construct the new list from CURRENT LOCATION + This list
        val newList = ArrayList<FavoriteCity>()

        // Add the current location, but only if we have location enabled and we can find it
        if (locationPermissionGranted) {
            try {
                val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                location?.run {
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 2)
                    if (addresses.size > 0) {
                        val city = FavoriteCity().apply { setLocation(addresses[0], true) }
                        newList.add(city)
                    }
                }
            } catch (securityException: SecurityException) {
                Timber.e(securityException)
                locationPermissionGranted = false
            }
        }

        // Add the favorite cities
        vm.favoriteCities.value?.let { newList.addAll(it) }

        // Call .setResults() to update the list
        favoritesAdapter.setResults(newList)
    }

    /**
     * LocationListener - called when the location has changed
     */
    override fun onLocationChanged(location: Location?) = updateFavoritesAdapter()

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) { }
    override fun onProviderEnabled(provider: String?) { }
    override fun onProviderDisabled(provider: String?) { }
}
