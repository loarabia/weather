package com.github.adrianhall.weather.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.auth.AuthenticatedUser
import com.github.adrianhall.weather.auth.FacebookLoginManager
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val fbManager = FacebookLoginManager()
    private val vm by viewModel<LoginViewModel>()

    /**
     * Android lifecycle - called when the activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fbManager
            .onSuccess { user -> moveToNextActivity(user) }
            .onError   { error -> displayAlert(error) }

        facebookLoginButton.setOnClickListener {
            fbManager.beginInteractiveSignin(this@LoginActivity)
        }
    }

    /**
     * Android lifecycle - called once the activity is ready for input
     */
    override fun onResume() {
        super.onResume()
        fbManager.beginSilentSignin(this)
    }

    /**
     * Android lifecycle - called when the control has switched to another activity so that
     * "information" can be gathered, and that "information" is returned.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fbManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun moveToNextActivity(user: AuthenticatedUser) {
        vm.setUser(user)
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
