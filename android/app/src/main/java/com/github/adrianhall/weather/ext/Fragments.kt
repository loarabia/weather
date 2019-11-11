package com.github.adrianhall.weather.ext

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun FragmentManager.replaceFragment(@IdRes id: Int, fragment: Fragment) {
    beginTransaction().replace(id, fragment).commit()
}

fun FragmentManager.addFragment(@IdRes id: Int, fragment: Fragment) {
    beginTransaction().add(id, fragment).commit()
}

fun FragmentManager.upsertFragment(@IdRes id: Int, fragment: Fragment) {
    if (fragments.isEmpty()) {
        addFragment(id, fragment)
    } else {
        replaceFragment(id, fragment)
    }
}