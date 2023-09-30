package com.example.bervageorder

import android.app.Application
import timber.log.Timber

class BeverageOrderApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}