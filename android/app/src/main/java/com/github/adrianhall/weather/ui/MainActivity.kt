package com.github.adrianhall.weather.ui

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
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var fragmentList: HashMap<Int, Fragment>
    private lateinit var buttonText: HashMap<Int, Int>
    private val vm = viewModel<LoginViewModel>()
    private val fbManager by inject<FacebookLoginManager>()

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
        setCurrentFragmentTo(R.id.mainFavoritesButton)

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

        // Change the existing fragment (if any)
        supportFragmentManager.run {
            if (fragments.isNotEmpty()) {
                beginTransaction()
                    .replace(R.id.mainFragment, newFragment)
                    .commit()
            } else {
                beginTransaction()
                    .add(R.id.mainFragment, newFragment)
                    .commit()
            }
        }

        // Enable/disable the bottom app bar icons
        fragmentList.keys.forEach { buttonId ->
            findViewById<ImageButton>(buttonId).isEnabled = (buttonId != id)
            findViewById<TextView>(buttonText[buttonId]!!).isEnabled = (buttonId != id)
        }

        // Sets the title of the toolbar.
        supportActionBar?.title = if (newFragment is TabFragment) newFragment.title else newFragment.javaClass.simpleName
    }
}
