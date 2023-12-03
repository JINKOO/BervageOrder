package com.example.bervageorder

import timber.log.Timber

class BeverageOrderApplicationDebug : BeverageOrderApplication() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(timberTree)
        Timber.d("BeverageOrderApplicationDebug() :: Logger")
    }
}
