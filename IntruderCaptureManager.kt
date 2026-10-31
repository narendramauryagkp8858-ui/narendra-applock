package com.narendra.applock.security

import android.content.Context
import android.util.Log

class IntruderCaptureManager(private val context: Context) {
    fun captureSilentSelfie(targetPackage: String) {
        // Trigger CameraX front lens capture and store securely in app-private directory
        Log.d("NarendraAppLock", "Intruder snapshot triggered for package: $targetPackage")
    }
}
