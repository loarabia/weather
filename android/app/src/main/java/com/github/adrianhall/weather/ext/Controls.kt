package com.github.adrianhall.weather.ext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.widget.SearchView

fun SearchView.onTextChanged(callback: (String?) -> Unit) {
    this.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            callback(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            callback(newText)
            return false
        }
    })
}

fun EditText.onTextChanged(callback: (String?) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) { callback(s.toString()) }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}