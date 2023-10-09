package com.example.bervageorder

import android.app.Application
import androidx.startup.AppInitializer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class BeverageOrderApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        AppInitializer.getInstance(this)
            .initializeComponent(LoggerInitializer::class.java)
    }
}