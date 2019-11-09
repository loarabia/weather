package com.github.adrianhall.weather.ui

import androidx.lifecycle.ViewModel
import com.github.adrianhall.weather.auth.AuthenticatedUser
import com.github.adrianhall.weather.auth.AuthenticationRepository

/**
 * View model for the login page.
 */
class LoginViewModel(private val repository: AuthenticationRepository): ViewModel() {
    /**
     * Allows you to watch the authenticated user through an observable.
     */
    val user = repository.user

    /**
     * Sets the authenticated user within the repository
     */
    fun setUser(user: AuthenticatedUser) = repository.setAuthenticatedUser(user)

    /**
     * Clears the authenticated user within the repositoru
     */
    fun clearUser() = repository.clearAuthenticatedUser()
}