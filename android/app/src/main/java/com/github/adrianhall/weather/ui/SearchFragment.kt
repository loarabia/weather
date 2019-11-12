package com.github.adrianhall.weather.ui

import android.app.Activity
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.ext.onTextChanged
import com.github.adrianhall.weather.models.FavoriteCity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import timber.log.Timber
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * Search Fragment
 *
 * The purpose here is to present a search box and display the results of the search below
 * the search box in a list, in such a way that it can be navigated.  We use the Android Geocoder
 * interface to implement the search capability.
 */
class SearchFragment : Fragment(), TabFragment {
    companion object {
        // Max number of results during the search
        private const val MAX_RESULTS = 20
    }

    private var compositeDisposable = CompositeDisposable()
    private lateinit var geocoder: Geocoder
    private lateinit var searchResultsAdapter: SearchResultsAdapter

    /**
     * Part of TabFragment - called to get the name of this tab.
     */
    override val title = context?.getString(R.string.tab_search) ?: "Search"

    /**
     * Android lifecycle - called when the fragment view is created
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        val view = inflater.inflate(R.layout.search_fragment, container, false)

        // Initialize the geocoder
        geocoder = Geocoder(context)

        // Initialize the searchbox
        configureSearchBox(view.findViewById(R.id.search_box))

        // Initialize the recylerview with no contents
        searchResultsAdapter = SearchResultsAdapter { address ->
            Timber.d("Clicked on address: ${address.locality}, ${address.countryName}")
            val city = FavoriteCity().apply { setLocation(address, false) }
            DetailsActivity.startActivity(activity as Activity, city)
        }
        val searchResultsView = view.findViewById<RecyclerView>(R.id.search_results_view)
        searchResultsView.adapter = searchResultsAdapter

        return view
    }

    /**
     * Android lifecycle - called when the fragment is destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    /**
     * We have a search box on the display.  This will configure the search box so that
     * performSearch() is called when there is a valid search to be performed.
     */
    private fun configureSearchBox(search_box: EditText) {
        val emitter = ObservableOnSubscribe<String> { emitter ->
            search_box.onTextChanged { text -> emitter.onNext(text!!) }
        }
        var disposable = Observable.create(emitter)
            .map { text -> text.toLowerCase().trim() }
            .debounce(250, TimeUnit.MILLISECONDS)
            .distinct()
            .filter { text -> text.isNotBlank() && text.length > 3}
            .subscribe { text -> performSearch(text) }
        compositeDisposable.add(disposable)
    }

    /**
     * Performs the actual search for cities, updating the recyclerView to show the results.
     */
    private fun performSearch(searchString: String) {
        Timber.d("Search String = $searchString")
        val addresses = geocoder.getFromLocationName(searchString, MAX_RESULTS)
        activity?.runOnUiThread { searchResultsAdapter.setResults(addresses) }
    }
}
