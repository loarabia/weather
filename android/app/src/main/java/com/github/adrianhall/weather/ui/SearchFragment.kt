package com.github.adrianhall.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.adrianhall.weather.R
import org.koin.android.viewmodel.ext.android.viewModel

class SearchFragment : Fragment(), TabFragment {
    private val vm by viewModel<SearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, state: Bundle?): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override val title = context?.getString(R.string.tab_search) ?: "Search"

    // TODO: Add a search box control with RxJava observer for searching
    // TODO: Add a recyclerlist for displaying results
    // TODO: Add navigation to the recyclerlist to load the details page
}
