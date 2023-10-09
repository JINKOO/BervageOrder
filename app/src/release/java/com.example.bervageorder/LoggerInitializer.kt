package com.example.bervageorder

import android.content.Context
import androidx.startup.Initializer

class LoggerInitializer : Initializer<Unit> {
    override fun create(context: Context) = Unit

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
