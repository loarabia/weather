package com.github.adrianhall.weather

import android.app.Application
import com.facebook.FacebookSdk
import com.github.adrianhall.weather.auth.AuthenticationRepository
import com.github.adrianhall.weather.auth.FacebookLoginManager
import com.github.adrianhall.weather.ui.FavoritesViewModel
import com.github.adrianhall.weather.ui.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

@Suppress("unused")
class ApplicationWrapper : Application() {
    companion object {
        val services = module {
            single { FacebookLoginManager() }
            single { AuthenticationRepository() }
        }

        val viewModels = module {
            viewModel { LoginViewModel(get())  }
            viewModel { FavoritesViewModel()   }
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Logging initialization
        if (BuildConfig.DEBUG) {
            // Initialize console logging
            Timber.plant(Timber.DebugTree())
        }

        // Initialize the Facebook SDK
        FacebookSdk.fullyInitialize()

        // Initialize dependency injection
        startKoin {
            androidLogger()
            androidContext(this@ApplicationWrapper)
            modules(listOf(services, viewModels))
        }
    }
}
