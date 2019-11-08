package com.github.adrianhall.weather.auth

data class AuthenticatedUser(
    val accessToken: String,
    val provider: AuthenticationProvider,
    val properties: Map<String,String>
)