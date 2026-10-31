-keep class androidx.biometric.** { *; }
-keep class androidx.camera.** { *; }
-dontwarn androidx.camera.**
-keepclassmembers class * {
    @androidx.room.* *;
}
