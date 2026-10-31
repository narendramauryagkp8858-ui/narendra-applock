package com.narendra.applock.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import com.narendra.applock.security.SecurityPreferences
import com.narendra.applock.ui.LockScreenActivity

class AppLockAccessibilityService : AccessibilityService() {

    private lateinit var preferences: SecurityPreferences
    private var lastUnlockedPackage: String? = null

    override fun onServiceConnected() {
        super.onServiceConnected()
        preferences = SecurityPreferences(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null || event.eventType != AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) return

        val packageName = event.packageName?.toString() ?: return
        if (packageName == applicationContext.packageName) return

        // Verify if package is locked and requires PIN
        if (preferences.isAppLocked(packageName) && packageName != lastUnlockedPackage) {
            val lockIntent = Intent(this, LockScreenActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                putExtra("TARGET_PACKAGE", packageName)
            }
            startActivity(lockIntent)
        }
    }

    override fun onInterrupt() {}
}
