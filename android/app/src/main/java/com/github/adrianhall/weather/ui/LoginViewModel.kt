package com.github.adrianhall.weather.ui

import androidx.lifecycle.ViewModel
import com.github.adrianhall.weather.auth.AuthenticatedUser
import com.github.adrianhall.weather.auth.AuthenticationRepository

/**
 * View model for the login page.
 */
class LoginViewModel(private val repository: AuthenticationRepository): ViewModel() {
    /**
     * Sets the authenticated user within the repository
     */
    fun setUser(user: AuthenticatedUser) = repository.setAuthenticatedUser(user)
}