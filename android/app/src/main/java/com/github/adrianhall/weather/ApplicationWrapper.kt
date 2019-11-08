package com.github.adrianhall.weather

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

@Suppress("unused")
class ApplicationWrapper : Application() {
    companion object {
        val services = module {
        }

        val viewModels = module {
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Logging initialization
        if (BuildConfig.DEBUG) {
            // Initialize console logging
            Timber.plant(Timber.DebugTree())
        }

        // Initialize dependency injection
        startKoin {
            androidLogger()
            androidContext(this@ApplicationWrapper)
            modules(listOf(services, viewModels))
        }
    }
}
