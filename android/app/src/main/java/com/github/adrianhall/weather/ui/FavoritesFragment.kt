package com.github.adrianhall.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.adrianhall.weather.R
import org.koin.android.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment(), TabFragment {
    private val vm by viewModel<FavoritesViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override val title = context?.getString(R.string.tab_favorites) ?: "Favorites"

    // TODO: Add a recyclerlist for the favorites.
    // TODO: Add current location
    // TODO: add navigation to the details page
}
