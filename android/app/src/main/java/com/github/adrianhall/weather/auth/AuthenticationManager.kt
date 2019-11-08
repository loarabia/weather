package com.github.adrianhall.weather.auth

import android.app.Activity
import android.content.Context
import android.content.Intent

typealias OnAuthSuccessCallback   = (AuthenticatedUser) -> Unit
typealias OnAuthErrorCallback     = (Exception?) -> Unit
typealias OnAuthCancelledCallback = () -> Unit

interface AuthenticationManager {
    /**
     * Establish a callback that is called when the user is successfully authenticated
     */
    fun onSuccess(callback: OnAuthSuccessCallback): AuthenticationManager

    /**
     * Establish a callback that is called when the user cancels the authentication
     */
    fun onCancelled(callback: OnAuthCancelledCallback): AuthenticationManager

    /**
     * Establish a callback that is called when there is an error authenticating the user
     */
    fun onError(callback: OnAuthErrorCallback): AuthenticationManager

    /**
     * Initiate an interactive sign-in session.
     */
    fun beginInteractiveSignin(activity: Activity)

    /**
     * Initiate a silent sign-in session.
     */
    fun beginSilentSignin(context: Context)

    /**
     * When the user initiates an interactive sign-in, it generally calls out "somewhere else"
     * This will handle the response.  Call this from your LoginActivity onActivityResult() method.
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}