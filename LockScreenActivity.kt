package com.narendra.applock.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.narendra.applock.databinding.ActivityLockScreenBinding
import com.narendra.applock.security.IntruderCaptureManager
import com.narendra.applock.security.SecurityPreferences

class LockScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockScreenBinding
    private lateinit var preferences: SecurityPreferences
    private lateinit var intruderManager: IntruderCaptureManager
    private var failedAttempts = 0
    private var targetPackage: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = SecurityPreferences(this)
        intruderManager = IntruderCaptureManager(this)
        targetPackage = intent.getStringExtra("TARGET_PACKAGE") ?: ""

        setupKeypad()
        initBiometricPrompt()
    }

    private fun setupKeypad() {
        // Handle PIN input digits 0-9
        // Validate against hash stored in EncryptedSharedPreferences
    }

    private fun handleFailedAttempt() {
        failedAttempts++
        if (failedAttempts >= preferences.getIntruderThreshold()) {
            intruderManager.captureSilentSelfie(targetPackage)
        }
    }

    private fun initBiometricPrompt() {
        val executor = ContextCompat.getMainExecutor(this)
        val prompt = BiometricPrompt(this, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                finish()
            }
        })
    }

    override fun onBackPressed() {
        // Send user back to home screen if they cancel lock
        val startMain = android.content.Intent(android.content.Intent.ACTION_MAIN).apply {
            addCategory(android.content.Intent.CATEGORY_HOME)
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(startMain)
        finish()
    }
}
