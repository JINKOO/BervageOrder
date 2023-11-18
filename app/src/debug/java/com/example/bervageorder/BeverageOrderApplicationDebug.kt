package com.example.bervageorder

import timber.log.Timber

class BeverageOrderApplicationDebug : BeverageOrderApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.d("BeverageOrderApplicationDebug() :: Logger")
    }
}