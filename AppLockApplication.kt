package com.narendra.applock

import android.app.Application

class AppLockApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize global security preferences and background monitors
    }
}
