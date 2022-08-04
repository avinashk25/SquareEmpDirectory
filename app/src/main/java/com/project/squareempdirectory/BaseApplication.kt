package com.project.squareempdirectory

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * Base application class for the app.
 */

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        private lateinit var sInstance: BaseApplication

        private fun getInstance(): BaseApplication {
            return sInstance
        }

        fun getApplicationContext(): Context {
            return getInstance()
        }
    }
}