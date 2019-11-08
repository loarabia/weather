package com.github.adrianhall.weather.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult

/**
 * Implementation of the authentication manager for Facebook logins.
 */
class FacebookLoginManager : AuthenticationManager {
    private val fbLoginManager = LoginManager.getInstance()
    private val fbCallbackManager = CallbackManager.Factory.create()
    private var onSuccessCallback: OnAuthSuccessCallback? = null
    private var onErrorCallback: OnAuthErrorCallback? = null
    private var onCancelledCallback: OnAuthCancelledCallback? = null

    // List of permissions we need to ask for when logging in
    var permissions = mutableListOf("public_profile", "email")

    // Initialization of the facebook sub-system requires the registration of a callback
    // to handle the response from Facebook.
    init {
        fbLoginManager.registerCallback(fbCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null) {
                    getProfile(result.accessToken) { user ->
                        onSuccessCallback?.invoke(user)
                    }
                } else {
                    onErrorCallback?.invoke(RuntimeException("Auth success, but result is null"))
                }
            }

            override fun onCancel() { onCancelledCallback?.invoke() }

            override fun onError(error: FacebookException?) { onErrorCallback?.invoke(error) }
        })
    }

    /**
     * Initiate an interactive login
     *
     * @param activity the calling activity (the one with the onActivityResult() in it)
     */
    override fun beginInteractiveSignin(activity: Activity) {
        fbLoginManager.logInWithReadPermissions(activity, permissions)
    }

    /**
     * Initiate a silent login
     *
     * @param context the application context to use.
     */
    override fun beginSilentSignin(context: Context) {
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null && !accessToken.isExpired) {
            getProfile(accessToken) { user ->
                onSuccessCallback?.invoke(user)
            }
        }
    }

    /**
     * Establish a callback that is used when the authentication is successful
     */
    override fun onSuccess(callback: OnAuthSuccessCallback): AuthenticationManager {
        this.onSuccessCallback = callback
        return this
    }

    /**
     * Establish a callback that is used when the authentication is cancelled by the user
     */
    override fun onCancelled(callback: OnAuthCancelledCallback): AuthenticationManager {
        this.onCancelledCallback = callback
        return this
    }

    /**
     * Establish a callback that is used when the authentication fails for any reason
     */
    override fun onError(callback: OnAuthErrorCallback): AuthenticationManager {
        this.onErrorCallback = callback
        return this
    }

    /**
     * Response handler for the authentication request.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fbCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    /**
     * Retrieve the profile of the user
     *
     * @param accessToken the Facebook access token
     * @return (via callback) the user record
     */
    private fun getProfile(accessToken: AccessToken, callback: OnAuthSuccessCallback) {
        val request = GraphRequest.newMeRequest(accessToken) { json, response ->
            if (response.error != null) {
                onErrorCallback?.invoke(response.error.exception)
            } else {
                val props = mutableMapOf("user_id" to accessToken.userId)
                if (json.has("name")) props["name"] = json.getString("name")
                if (json.has("email")) props["email"] = json.getString("email")
                val user = AuthenticatedUser(accessToken.token, AuthenticationProvider.FACEBOOK, props)
                callback.invoke(user)
            }
        }
        request.parameters = Bundle().apply { putString("fields", "id,name,email") }
        request.executeAsync()
    }
}