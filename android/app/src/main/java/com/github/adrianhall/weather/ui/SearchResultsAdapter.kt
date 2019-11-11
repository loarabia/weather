package com.github.adrianhall.weather.ui

import android.location.Address
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.adrianhall.weather.R

/**
 * Adapter for the search results recycler view.
 *
 * Ensure you set a click handler when creating the adapter.
 *
 * Use `.setResults()` to set the results - the initial set of results is blank,
 */
class SearchResultsAdapter(private val onClickHandler: (Address) -> Unit): RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {
    private var searchResults: ArrayList<Address> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_result_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = searchResults.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setAddress(searchResults[position])
        holder.onClick { address -> onClickHandler.invoke(address) }
    }

    /**
     * Use `.setResults()` to show off some new results.
     */
    fun setResults(results: List<Address>) {
        searchResults.clear()
        searchResults.addAll(results)
        notifyDataSetChanged()
    }

    /**
     * Class to convert a single result into a view.
     */
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val locality = itemView.findViewById<TextView>(R.id.search_result_locality)
        private val location = itemView.findViewById<TextView>(R.id.search_result_location)
        private val localityFmt = itemView.context.getString(R.string.locality_format)
        private val locationFmt = itemView.context.getString(R.string.location_format)
        private var currentAddress: Address? = null

        fun setAddress(address: Address) {
            currentAddress = address
            locality.text = localityFmt.format(address.locality, address.countryName)
            location.text = locationFmt.format(address.latitude, address.longitude)
        }

        fun onClick(onClickHandler: (address: Address) -> Unit) {
            itemView.setOnClickListener { onClickHandler.invoke(currentAddress!!) }
        }
    }
}