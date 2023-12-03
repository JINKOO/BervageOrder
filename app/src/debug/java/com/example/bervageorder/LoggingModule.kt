package com.example.bervageorder

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
object LoggingModule {

    @Provides
    fun provideLogTag(
        @ApplicationContext context: Context,
    ): String {
        return context.getString(R.string.app_name)
    }

    @Provides
    fun provideTimberTree(
        tag: String,
    ): Timber.Tree {
        return DebugTree(tag)
    }

    class DebugTree(
        private val tag: String,
    ) : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            super.log(priority, this.tag, "$tag - $message", t)
            if (priority == Log.ERROR || priority == Log.WARN) {
//                t?.let(Firebase.crashlytics::recordException)
//                Firebase.crashlytics.log(message)
            }
        }

        override fun createStackElementTag(element: StackTraceElement): String {
            return "(${element.fileName}:${element.lineNumber})#${element.methodName}"
        }
    }
}
