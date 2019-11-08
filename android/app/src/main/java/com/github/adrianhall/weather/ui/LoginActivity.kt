package com.github.adrianhall.weather.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.auth.AuthenticatedUser
import com.github.adrianhall.weather.auth.FacebookLoginManager

class LoginActivity : AppCompatActivity() {
    private val fbManager = FacebookLoginManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fbManager
            .onSuccess { user -> moveToNextActivity(user) }
            .onError   { error -> displayAlert(error) }

        // TODO: Layout for the login activity, which includes the facebookLoginButton
        //facebookLoginButton.setOnClickListener {
        //    fbManager.beginInteractiveSignin(this@LoginActivity)
        //}
    }

    override fun onResume() {
        super.onResume()
        fbManager.beginSilentSignin(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fbManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun moveToNextActivity(user: AuthenticatedUser) {
        // TODO: Store the authenticated user in the AuthenticationRepository
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun displayAlert(error: Exception?) {
        val alert = AlertDialog.Builder(this@LoginActivity)
            .setMessage(error?.message ?: "Unknown error")
            .setTitle("Authentication Error")
            .setCancelable(false)
            .setPositiveButton("OK") { _,_ -> finish() }
            .create()
        alert.show()
    }
}
