package com.github.adrianhall.weather.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/**
 * Repository that holds the authenticated user information.  This is accessed through a
 * LiveData<> observable, so it can be accessed anywhere relatively easily.  If the value is
 * null, then the user is not authenticated.
 */
class AuthenticationRepository {
    private var mUser: MutableLiveData<AuthenticatedUser?> = MutableLiveData()
    var user: LiveData<AuthenticatedUser?> = mUser

    fun setAuthenticatedUser(user: AuthenticatedUser) {
        mUser.postValue(user)
    }
}