package com.github.adrianhall.weather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.models.FavoriteCity

/**
 * Adapter for the search results recycler view.
 *
 * Ensure you set a click handler when creating the adapter.
 *
 * Use `.setResults()` to set the results - the initial set of results is blank,
 */
class FavoritesAdapter(private val onClickHandler: (FavoriteCity) -> Unit): RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    private var listContents: ArrayList<FavoriteCity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listContents.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setBinding(listContents[position])
        holder.onClick { entry -> onClickHandler.invoke(entry) }
    }

    /**
     * Use `.setResults()` to show off some new results.
     */
    fun setResults(results: List<FavoriteCity>) {
        listContents.clear()
        listContents.addAll(results)
        notifyDataSetChanged()
    }

    /**
     * Class to convert a single result into a view.
     */
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // Determine the resources that we need to populate
        private val locality = itemView.findViewById<TextView>(R.id.favorite_locality)
        private val location = itemView.findViewById<TextView>(R.id.favorite_location)
        private val typeImage = itemView.findViewById<ImageView>(R.id.favorite_type)

        // We format location with lat/long - this is the formatter for that
        private val locationFmt = itemView.context.getString(R.string.location_format)

        // current entry (for click-handling)
        private var currentEntry: FavoriteCity? = null

        fun setBinding(entry: FavoriteCity) {
            currentEntry = entry

            // Fill in the view holder with information
            locality.text = entry.displayName
            location.text = locationFmt.format(entry.latitude, entry.longitude)
            if (entry.isCurrentLocation) {
                typeImage.setImageResource(R.drawable.type_current)
            } else {
                typeImage.setImageResource(R.drawable.type_favorite)
            }
        }

        fun onClick(onClickHandler: (address: FavoriteCity) -> Unit) {
            itemView.setOnClickListener { onClickHandler.invoke(currentEntry!!) }
        }
    }
}