package com.github.adrianhall.weather.ui

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.github.adrianhall.weather.R
import com.github.adrianhall.weather.auth.FacebookLoginManager
import com.github.adrianhall.weather.ext.upsertFragment
import com.google.android.material.tabs.TabLayout
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentList: HashMap<Int, Fragment>
    private lateinit var buttonText: HashMap<Int, Int>
    private val vm = viewModel<LoginViewModel>()
    private val fbManager by inject<FacebookLoginManager>()
    private var locationPermissionsGranted = false

    /**
     * Android lifecycle - called when the activity is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainToolbar)

        // Create the list of fragments
        fragmentList = hashMapOf(
            R.id.mainFavoritesButton to FavoritesFragment(),
            R.id.mainSearchButton    to SearchFragment()
        )
        buttonText = hashMapOf(
            R.id.mainFavoritesButton to R.id.mainFavoritesText,
            R.id.mainSearchButton    to R.id.mainSearchText
        )

        // Ask for permissions to use location prior to establishing the fragment
        Dexter.withActivity(this@MainActivity)
            .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object: MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            locationPermissionsGranted = true
                            setCurrentFragmentTo(R.id.mainFavoritesButton)
                        } else {
                            locationPermissionsGranted = false
                            setCurrentFragmentTo(R.id.mainSearchButton)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
                    // Remember to invoke this method when the custom rationale is closed
                    // or just by default if you don't want to use any custom rationale.
                    token?.continuePermissionRequest()
                }
            })
            .check()

        // Wire up the fragment buttons
        buttonText.keys.forEach { buttonId ->
            val button = findViewById<ImageButton>(buttonId)
            button.setOnClickListener { setCurrentFragmentTo(buttonId) }

            val textView = findViewById<TextView>(buttonText[buttonId]!!)
            textView.setOnClickListener { setCurrentFragmentTo(buttonId) }
        }
    }

    /**
     * Android lifecycle - hydrates the icons in the action bar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.signout_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_signout -> {
                fbManager.signOut()
                vm.value.clearUser()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     *  Given a specific ID, we need to
     *  1) Replace any fragment that is displayed now
     *  2) Disable the current button and enable the others
     *  3) Set the title of the page
     */
    private fun setCurrentFragmentTo(@IdRes id: Int) {
        val newFragment = fragmentList[id]!!

        // Don't do anything if the favorites list has been requested and location services
        // is turned off
        if (id == R.id.mainFavoritesButton && !locationPermissionsGranted) {
            contentView?.snackbar("Location services were denied")
        }

        // Change the existing fragment (if any)
        supportFragmentManager.run { upsertFragment(R.id.mainFragment, newFragment) }

        // Enable/disable the bottom app bar icons
        fragmentList.keys.forEach { buttonId ->
            findViewById<ImageButton>(buttonId).isEnabled = (buttonId != id)
            findViewById<TextView>(buttonText[buttonId]!!).isEnabled = (buttonId != id)
        }

        // Sets the title of the toolbar.
        supportActionBar?.title = if (newFragment is TabFragment) newFragment.title else newFragment.javaClass.simpleName
    }
}
