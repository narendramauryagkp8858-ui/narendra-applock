package com.narendra.applock.security

import android.content.Context
import android.content.SharedPreferences

class SecurityPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("narendra_applock_prefs", Context.MODE_PRIVATE)

    fun isAppLocked(packageName: String): Boolean {
        val lockedSet = prefs.getStringSet("locked_packages", emptySet()) ?: emptySet()
        return lockedSet.contains(packageName)
    }

    fun setAppLocked(packageName: String, locked: Boolean) {
        val currentSet = HashSet(prefs.getStringSet("locked_packages", emptySet()) ?: emptySet())
        if (locked) currentSet.add(packageName) else currentSet.remove(packageName)
        prefs.edit().putStringSet("locked_packages", currentSet).apply()
    }

    fun getPin(): String = prefs.getString("pin_code", "2580") ?: "2580"
    fun setPin(pin: String) = prefs.edit().putString("pin_code", pin).apply()

    fun getIntruderThreshold(): Int = prefs.getInt("intruder_threshold", 1)
    fun isIntruderSelfieEnabled(): Boolean = prefs.getBoolean("intruder_selfie", true)
}
